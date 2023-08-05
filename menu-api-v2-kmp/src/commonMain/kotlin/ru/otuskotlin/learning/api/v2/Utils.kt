package ru.otuskotlin.learning.api.v2

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import ru.otuskotlin.learning.api.v2.models.*
import kotlin.reflect.KClass

internal data class PolymorphicInfo<S : Any, T : S>(
    val klass: KClass<T>,
    val superClass: KClass<S>,
    val serialize: (Encoder, Any) -> Unit,
    val discriminator: String,
    val makeCopyWithDiscriminator: (Any) -> T
)

@OptIn(InternalSerializationApi::class)
@Suppress("UNCHECKED_CAST")
internal fun <S : Any, T : S> info(
    klass: KClass<T>, superClass: KClass<S>, discriminator: String, makeCopyWithDiscriminator: T.(String) -> T
) = PolymorphicInfo(klass, superClass, { e: Encoder, v: Any ->
    klass.serializer().serialize(e, v as T)
}, discriminator, { v: Any ->
    makeCopyWithDiscriminator(v as T, discriminator)
})

private inline fun findInfo(
    klass: KClass<*>, error: String, predicate: PolymorphicInfo<out Any, out Any>.() -> Boolean
) = infos.firstOrNull {
    it.superClass == klass && it.predicate()
} ?: throw SerializationException(error)

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
private inline fun <reified T : Any> SerializersModuleBuilder.polymorphicSerializer() {
    polymorphicDefaultSerializer(T::class) { value ->
        val info = findInfo(T::class, "Unknown class to serialize ${value::class}") { klass == value::class }
        object : KSerializer<T> {
            override val descriptor: SerialDescriptor
                get() = info.klass.serializer().descriptor

            override fun serialize(encoder: Encoder, value: T) {
                val copy = info.makeCopyWithDiscriminator(value)
                info.serialize(encoder, copy)
            }

            override fun deserialize(decoder: Decoder): T = throw NotImplementedError("you should not use this method")
        }
    }
}

@OptIn(InternalSerializationApi::class)
private class PolymorphicSerializer<T : Any>(private val klass: KClass<T>, private val discriminatorField: String) :
    JsonContentPolymorphicSerializer<T>(klass) {
    @Suppress("UNCHECKED_CAST")
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out T> {
        val discriminatorValue = element.jsonObject[discriminatorField]?.jsonPrimitive?.content
        val info = findInfo(klass, "Unknown class to deserialize: $discriminatorValue") {
            discriminator == discriminatorValue
        }
        return info.klass.serializer() as DeserializationStrategy<out T>
    }
}

private val requestSerializer = PolymorphicSerializer(IRequestDto::class, "requestType")
private val responseSerializer = PolymorphicSerializer(IResponseDto::class, "responseType")

@OptIn(ExperimentalSerializationApi::class)
internal fun SerializersModuleBuilder.setupPolymorphic() {
    polymorphicSerializer<IRequestDto>()
    polymorphicDefaultDeserializer(IRequestDto::class) { requestSerializer }

    polymorphicSerializer<IResponseDto>()
    polymorphicDefaultDeserializer(IResponseDto::class) { responseSerializer }

    contextual(requestSerializer)
    contextual(responseSerializer)
}

fun apiKmpRequestSerialize(request: IRequestDto): String = apiKmpMapper.encodeToString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequestDto> apiKmpRequestDeserialize(json: String): T = apiKmpMapper.decodeFromString<IRequestDto>(json) as T

fun apiKmpResponseSerialize(response: IResponseDto): String = apiKmpMapper.encodeToString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponseDto> apiKmpResponseDeserialize(json: String): T =
    apiKmpMapper.decodeFromString<IResponseDto>(json) as T