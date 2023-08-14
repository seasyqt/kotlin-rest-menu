import exceptions.UnknownRequestClass
import ru.otuskotlin.learning.GoodsContext
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.models.DebugMode
import ru.otuskotlin.learning.models.RequestId
import ru.otuskotlin.learning.models.goods.*
import ru.otuskotlin.learning.stubs.GoodsStub
import java.math.BigInteger

fun GoodsContext.fromTransport(request: IRequestDto) = when (request) {
    is GoodsCreateRequestDto -> fromTransport(request)
    is GoodsReadRequestDto -> fromTransport(request)
    is GoodsUpdateRequestDto -> fromTransport(request)
    is GoodsDeleteRequestDto -> fromTransport(request)
    is GoodsSearchRequestDto -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toGoodsId() = this?.let { GoodsId(it) } ?: GoodsId.NONE
private fun String?.toGoodsWithId() = Goods(id = this.toGoodsId())
private fun IRequestDto?.requestId() = this?.requestId?.let { RequestId(it) } ?: RequestId.NONE

private fun GoodsDebugDto?.transportToWorkMode(): DebugMode = when (this?.mode) {
    RequestDebugModeDto.PROD -> DebugMode.PROD
    RequestDebugModeDto.TEST -> DebugMode.TEST
    RequestDebugModeDto.STUB -> DebugMode.STUB
    null -> DebugMode.PROD
}

private fun GoodsDebugDto?.transportToStubCase(): GoodsStub = when (this?.stub) {
    GoodsRequestDebugStubsDto.SUCCESS -> GoodsStub.SUCCESS
    GoodsRequestDebugStubsDto.NOT_FOUND -> GoodsStub.NOT_FOUND
    GoodsRequestDebugStubsDto.BAD_ID -> GoodsStub.BAD_ID
    GoodsRequestDebugStubsDto.BAD_NAME -> GoodsStub.BAD_NAME
    GoodsRequestDebugStubsDto.BAD_TYPE -> GoodsStub.BAD_TYPE
    GoodsRequestDebugStubsDto.BAD_PRICE -> GoodsStub.BAD_PRICE
    GoodsRequestDebugStubsDto.CANNOT_DELETE -> GoodsStub.CANNOT_DELETE
    GoodsRequestDebugStubsDto.BAD_SEARCH_STRING -> GoodsStub.BAD_SEARCH_STRING
    null -> GoodsStub.NONE
}

fun GoodsContext.fromTransport(request: GoodsCreateRequestDto) {
    command = GoodsCommand.CREATE
    requestId = request.requestId()
    goodsRequest = request.goods?.toInternal() ?: Goods()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun GoodsContext.fromTransport(request: GoodsReadRequestDto) {
    command = GoodsCommand.READ
    requestId = request.requestId()
    goodsRequest = request.id.toGoodsWithId()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun GoodsContext.fromTransport(request: GoodsUpdateRequestDto) {
    command = GoodsCommand.UPDATE
    requestId = request.requestId()
    goodsRequest = request.goods?.toInternal() ?: Goods()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun GoodsContext.fromTransport(request: GoodsDeleteRequestDto) {
    command = GoodsCommand.DELETE
    requestId = request.requestId()
    goodsRequest = request.id.toGoodsWithId()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

fun GoodsContext.fromTransport(request: GoodsSearchRequestDto) {
    command = GoodsCommand.SEARCH
    requestId = request.requestId()
    goodsFilterRequest = request.goodsFilter.toInternal()
    debugMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

private fun GoodsSearchFilterDto?.toInternal(): GoodsFilter = GoodsFilter(
    nameSearch = this?.nameSearch ?: ""
)

private fun GoodsCreateObjectDto.toInternal(): Goods = Goods(
    name = this.name ?: "",
    type = this.type.fromTransport(),
    price = this.price?.toBigInteger() ?: BigInteger.ZERO,
    weight = this.weight ?: "",
)

private fun GoodsUpdateObjectDto.toInternal(): Goods = Goods(
    id = this.id.toGoodsId(),
    name = this.name ?: "",
    type = this.type.fromTransport(),
    price = this.price?.toBigInteger() ?: BigInteger.ZERO,
    weight = this.weight ?: "",
)

private fun GoodsTypeDto?.fromTransport(): GoodsType = when (this) {
    GoodsTypeDto.PIZZA -> GoodsType.PIZZA
    GoodsTypeDto.SOUP -> GoodsType.SOUP
    GoodsTypeDto.SALAD -> GoodsType.SALAD
    GoodsTypeDto.SNACK -> GoodsType.SNACK
    null -> GoodsType.NONE
}