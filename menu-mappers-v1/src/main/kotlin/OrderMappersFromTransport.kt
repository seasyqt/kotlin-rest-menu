import exceptions.UnknownRequestClass
import kotlinx.datetime.Instant
import ru.otuskotlin.learning.OrderContext
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.models.DebugMode
import ru.otuskotlin.learning.models.RequestId
import ru.otuskotlin.learning.models.goods.GoodsId
import ru.otuskotlin.learning.models.goods.GoodsType
import ru.otuskotlin.learning.models.order.*
import ru.otuskotlin.learning.stubs.OrderStub
import java.math.BigInteger

fun OrderContext.fromTransport(request: IRequestDto) = when (request) {
    is OrderCreateRequestDto -> fromTransport(request)
    is OrderReadRequestDto -> fromTransport(request)
    is OrderUpdateRequestDto -> fromTransport(request)
    is OrderDeleteRequestDto -> fromTransport(request)
    is OrderSearchRequestDto -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toOrderId() = this?.let { OrderId(it) } ?: OrderId.NONE
private fun String?.toGoodsId() = this?.let { GoodsId(it) } ?: GoodsId.NONE
private fun String?.toBuyerId() = this?.let { BuyerId(it) } ?: BuyerId.NONE
private fun String?.toOrderWithId() = Order(id = this.toOrderId())
private fun IRequestDto?.requestId() = this?.requestId?.let { RequestId(it) } ?: RequestId.NONE

private fun List<OrderGoodsWithIdObjectDto>.toGoodsList(): MutableList<GoodsOrder> =
    this.map { it.toInternal() }.toMutableList()

private fun BaseGoodsOrderDto.toGoodsList(): MutableList<GoodsOrder> =
    mutableListOf(this.toInternal())

private fun String?.toInstant(): Instant? = this?.let { Instant.parse(it) }


fun OrderDebugDto?.transportToWorkMode(): DebugMode = when (this?.mode) {
    RequestDebugModeDto.PROD -> DebugMode.PROD
    RequestDebugModeDto.TEST -> DebugMode.TEST
    RequestDebugModeDto.STUB -> DebugMode.STUB
    null -> DebugMode.PROD
}

private fun OrderDebugDto?.transportToStubCase(): OrderStub = when (this?.stub) {
    OrderRequestDebugStubsDto.SUCCESS -> OrderStub.SUCCESS
    OrderRequestDebugStubsDto.NOT_FOUND -> OrderStub.NOT_FOUND
    OrderRequestDebugStubsDto.BAD_ID -> OrderStub.BAD_ID
    OrderRequestDebugStubsDto.BAD_NAME -> OrderStub.BAD_NAME
    OrderRequestDebugStubsDto.BAD_TYPE -> OrderStub.BAD_TYPE
    OrderRequestDebugStubsDto.BAD_PRICE -> OrderStub.BAD_PRICE
    OrderRequestDebugStubsDto.CANNOT_DELETE -> OrderStub.CANNOT_DELETE
    OrderRequestDebugStubsDto.BAD_SEARCH_STRING -> OrderStub.BAD_SEARCH_STRING
    null -> OrderStub.NONE
}

fun OrderContext.fromTransport(request: OrderCreateRequestDto) {
    command = OrderCommand.CREATE
    requestId = request.requestId()
    orderRequest = request.order?.toInternal() ?: Order()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun OrderContext.fromTransport(request: OrderReadRequestDto) {
    command = OrderCommand.READ
    requestId = request.requestId()
    orderRequest = request.order?.toInternal() ?: Order()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun OrderContext.fromTransport(request: OrderUpdateRequestDto) {
    command = OrderCommand.UPDATE
    requestId = request.requestId()
    orderRequest = request.order?.toInternal() ?: Order()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun OrderContext.fromTransport(request: OrderDeleteRequestDto) {
    command = OrderCommand.DELETE
    requestId = request.requestId()
    orderRequest = request.order?.toInternal() ?: Order()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun OrderContext.fromTransport(request: OrderSearchRequestDto) {
    command = OrderCommand.SEARCH
    requestId = request.requestId()
    orderFilterRequest = request.goodsFilter?.toInternal() ?: OrderFilter()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

private fun OrderSearchFilterDto.toInternal(): OrderFilter = OrderFilter(
    buyerId = this.buyerId.toBuyerId(),
    dateFrom = this.dateFrom?.toInstant() ?: Instant.DISTANT_PAST,
    dateTo = this.dateTo?.toInstant() ?: Instant.DISTANT_FUTURE

)

private fun OrderIdObjectDto.toInternal(): Order = this.id.toOrderWithId()

private fun OrderGoodsWithIdObjectDto.toInternal(): GoodsOrder = GoodsOrder(
    id = this.id.toGoodsId(),
    name = this.name ?: "",
    price = this.price?.toBigInteger() ?: BigInteger.ZERO,
    count = this.count?.toBigInteger() ?: BigInteger.ZERO
)

private fun BaseGoodsOrderDto.toInternal(): GoodsOrder = GoodsOrder(
    id = GoodsId.NONE,
    name = this.name ?: "",
    price = this.price?.toBigInteger() ?: BigInteger.ZERO,
    count = this.count?.toBigInteger() ?: BigInteger.ZERO
)

private fun OrderUpdateObjectDto.toInternal(): Order = Order(
    id = this.id.toOrderId(),
    goodsList = this.goodsList?.toGoodsList() ?: mutableListOf()
)

private fun OrderGoodsObjectDto.toInternal(): Order = Order(
    buyerId = this.buyerId.toBuyerId(),
    goodsList = this.goods?.toGoodsList() ?: mutableListOf()
)


private fun GoodsTypeDto?.fromTransport(): GoodsType = when (this) {
    GoodsTypeDto.PIZZA -> GoodsType.PIZZA
    GoodsTypeDto.SOUP -> GoodsType.SOUP
    GoodsTypeDto.SALAD -> GoodsType.SALAD
    GoodsTypeDto.SNACK -> GoodsType.SNACK
    null -> GoodsType.NONE
}