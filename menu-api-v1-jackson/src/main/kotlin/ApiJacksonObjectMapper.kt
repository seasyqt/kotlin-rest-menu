import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import ru.otuskotlin.learning.api.v1.models.*


val apiJacksonObjectMapper = JsonMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

fun apiJacksonRequestSerialize(request: IRequestDto): String = apiJacksonObjectMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequestDto> apiJacksonRequestDeserialize(json: String): T =
    apiJacksonObjectMapper.readValue(json, IRequestDto::class.java) as T

fun apiJacksonResponseSerialize(response: IResponseDto): String = apiJacksonObjectMapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponseDto> apiJacksonResponseDeserialize(json: String): T =
    apiJacksonObjectMapper.readValue(json, IResponseDto::class.java) as T