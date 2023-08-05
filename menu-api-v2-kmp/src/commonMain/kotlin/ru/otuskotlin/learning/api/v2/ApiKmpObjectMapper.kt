package ru.otuskotlin.learning.api.v2

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import ru.otuskotlin.learning.api.v2.models.*


internal val infos = listOf(
    //Goods request
    info(GoodsCreateRequestDto::class, IRequestDto::class, "goodsCreate") { copy(requestType = it) },
    info(GoodsReadRequestDto::class, IRequestDto::class, "goodsRead") { copy(requestType = it) },
    info(GoodsUpdateRequestDto::class, IRequestDto::class, "goodsUpdate") { copy(requestType = it) },
    info(GoodsDeleteRequestDto::class, IRequestDto::class, "goodsDelete") { copy(requestType = it) },
    info(GoodsSearchRequestDto::class, IRequestDto::class, "goodsSearch") { copy(requestType = it) },

    //Goods response
    info(GoodsCreateResponseDto::class, IResponseDto::class, "goodsCreate") { copy(responseType = it) },
    info(GoodsReadResponseDto::class, IResponseDto::class, "goodsRead") { copy(responseType = it) },
    info(GoodsUpdateResponseDto::class, IResponseDto::class, "goodsUpdate") { copy(responseType = it) },
    info(GoodsDeleteResponseDto::class, IResponseDto::class, "goodsDelete") { copy(responseType = it) },
    info(GoodsSearchResponseDto::class, IResponseDto::class, "goodsSearch") { copy(responseType = it) },

    //Order request
    info(OrderCreateRequestDto::class, IRequestDto::class, "orderCreate") { copy(requestType = it) },
    info(OrderReadRequestDto::class, IRequestDto::class, "orderRead") { copy(requestType = it) },
    info(OrderUpdateRequestDto::class, IRequestDto::class, "orderUpdate") { copy(requestType = it) },
    info(OrderDeleteRequestDto::class, IRequestDto::class, "orderDelete") { copy(requestType = it) },
    info(OrderSearchRequestDto::class, IRequestDto::class, "orderSearch") { copy(requestType = it) },

    //Order response
    info(OrderCreateResponseDto::class, IResponseDto::class, "orderCreate") { copy(responseType = it) },
    info(OrderReadResponseDto::class, IResponseDto::class, "orderRead") { copy(responseType = it) },
    info(OrderUpdateResponseDto::class, IResponseDto::class, "orderUpdate") { copy(responseType = it) },
    info(OrderDeleteResponseDto::class, IResponseDto::class, "orderDelete") { copy(responseType = it) },
    info(OrderSearchResponseDto::class, IResponseDto::class, "orderSearch") { copy(responseType = it) },
)

val apiKmpMapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        setupPolymorphic()
    }
}