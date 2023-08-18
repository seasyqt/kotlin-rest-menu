package ru.otuskotlin.learning.menu.mappers

import exceptions.UnknownOrderCommand
import kotlinx.datetime.Instant
import ru.otuskotlin.learning.api.v1.models.*
import models.CommonError
import models.State
import models.goods.GoodsId
import models.order.*
import ru.otuskotlin.learning.menu.common.OrderContext
import utils.NONE
import java.math.BigInteger

fun OrderContext.toTransport(): IResponseDto = when (val cmd = command) {
    OrderCommand.CREATE -> toTransportCreate()
    OrderCommand.READ -> toTransportRead()
    OrderCommand.UPDATE -> toTransportUpdate()
    OrderCommand.DELETE -> toTransportDelete()
    OrderCommand.SEARCH -> toTransportSearch()
    OrderCommand.NONE -> throw UnknownOrderCommand(cmd)
}

fun OrderContext.toTransportCreate() = OrderCreateResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun OrderContext.toTransportRead() = OrderReadResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun OrderContext.toTransportUpdate() = OrderUpdateResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun OrderContext.toTransportDelete() = OrderDeleteResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun OrderContext.toTransportSearch() = OrderSearchResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = this.errors.toTransportErrors(),
    orderList = orderListResponse.toTransportOrder()
)

fun MutableList<Order>.toTransportOrder(): List<OrderResponseObjectDto>? = this
    .map { it.toTransportOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Order.toTransportOrder(): OrderResponseObjectDto = OrderResponseObjectDto(
    id = id.takeIf { it != OrderId.NONE }?.asString(),
    goodsList = goodsList.toTransportListGoods(),
    createDate = createDate.takeIf { it != Instant.NONE }?.toString(),
    totalPrice = totalPrice.toString(),
    buyerId = buyerId.takeIf { it != BuyerId.NONE }?.asString()
)

private fun MutableList<GoodsOrder>.toTransportListGoods(): List<OrderGoodsWithIdObjectDto>? = this
    .map { it.toTransportGoodsOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun GoodsOrder.toTransportGoodsOrder(): OrderGoodsWithIdObjectDto = OrderGoodsWithIdObjectDto(
    id = id.takeIf { it != GoodsId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    price = price.takeIf { it != BigInteger.ZERO }?.toString(),
    count = count.takeIf { it != BigInteger.ZERO }?.toString()
)
private fun Boolean.toResponseResult(): ResponseResultDto = when (this) {
    true -> ResponseResultDto.SUCCESS
    false -> ResponseResultDto.ERROR
}

private fun List<CommonError>.toTransportErrors(): List<ErrorDto>? = this
    .map { it.toTransportOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CommonError.toTransportOrder() = ErrorDto(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)