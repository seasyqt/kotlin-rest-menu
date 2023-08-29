package ru.otuskotlin.learning.menu.mappers

import exceptions.UnknownGoodsCommand
import ru.otuskotlin.learning.api.v1.models.*
import models.CommonError
import models.State
import models.goods.Goods
import models.goods.GoodsCommand
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.common.GoodsContext

fun GoodsContext.toTransport(): IResponseDto = when (val cmd = command) {
    GoodsCommand.CREATE -> toTransportCreate()
    GoodsCommand.READ -> toTransportRead()
    GoodsCommand.UPDATE -> toTransportUpdate()
    GoodsCommand.DELETE -> toTransportDelete()
    GoodsCommand.SEARCH -> toTransportSearch()
    GoodsCommand.NONE -> throw UnknownGoodsCommand(cmd)
}

fun GoodsContext.toTransportCreate() = GoodsCreateResponseDto(
    responseType = "goodsCreate",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.FINISHING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportGoods(),
    lock = goodsResponse.lock.asString()
)

fun GoodsContext.toTransportRead() = GoodsReadResponseDto(
    responseType = "goodsRead",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.FINISHING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportGoods()
)

fun GoodsContext.toTransportUpdate() = GoodsUpdateResponseDto(
    responseType = "goodsUpdate",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.FINISHING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportGoods()
)

fun GoodsContext.toTransportDelete() = GoodsDeleteResponseDto(
    responseType = "goodsDelete",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.FINISHING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportGoods()
)

fun GoodsContext.toTransportSearch() = GoodsSearchResponseDto(
    responseType = "goodsSearch",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.FINISHING).toResponseResult(),
    errors = this.errors.toTransportErrors(),
    goodsList = goodsListResponse.toTransportGoods()
)

fun MutableList<Goods>.toTransportGoods(): List<GoodsResponseObjectDto>? = this
    .map { it.toTransportGoods() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Goods.toTransportGoods(): GoodsResponseObjectDto = GoodsResponseObjectDto(
    id = id.takeIf { it != models.goods.GoodsId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    type = type.toTransportGoods(),
    price = price.toString(),
    weight = weight.takeIf { it.isNotBlank() }
)

private fun GoodsType.toTransportGoods(): GoodsTypeDto? = when (this) {
    GoodsType.PIZZA -> GoodsTypeDto.PIZZA
    GoodsType.SOUP -> GoodsTypeDto.SOUP
    GoodsType.SALAD -> GoodsTypeDto.SALAD
    GoodsType.SNACK -> GoodsTypeDto.SNACK
    GoodsType.NONE -> null
}

private fun Boolean.toResponseResult(): ResponseResultDto = when (this) {
    true -> ResponseResultDto.SUCCESS
    false -> ResponseResultDto.ERROR
}

private fun List<CommonError>.toTransportErrors(): List<ErrorDto>? = this
    .map { it.toTransportGoods() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CommonError.toTransportGoods() = ErrorDto(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)