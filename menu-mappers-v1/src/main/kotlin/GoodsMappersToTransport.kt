import exceptions.UnknownGoodsCommand
import ru.otuskotlin.learning.GoodsContext
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.models.CommonError
import ru.otuskotlin.learning.models.State
import ru.otuskotlin.learning.models.goods.Goods
import ru.otuskotlin.learning.models.goods.GoodsCommand
import ru.otuskotlin.learning.models.goods.GoodsId
import ru.otuskotlin.learning.models.goods.GoodsType

fun GoodsContext.toTransport(): IResponseDto = when (val cmd = command) {
    GoodsCommand.CREATE -> toTransportCreate()
    GoodsCommand.READ -> toTransportRead()
    GoodsCommand.UPDATE -> toTransportUpdate()
    GoodsCommand.DELETE -> toTransportDelete()
    GoodsCommand.SEARCH -> toTransportSearch()
    GoodsCommand.NONE -> throw UnknownGoodsCommand(cmd)
}

fun GoodsContext.toTransportCreate() = GoodsCreateResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportError()
)

fun GoodsContext.toTransportRead() = GoodsReadResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportError()
)

fun GoodsContext.toTransportUpdate() = GoodsUpdateResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportError()
)

fun GoodsContext.toTransportDelete() = GoodsDeleteResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = errors.toTransportErrors(),
    goods = goodsResponse.toTransportError()
)

fun GoodsContext.toTransportSearch() = GoodsSearchResponseDto(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = (state == State.RUNNING).toResponseResult(),
    errors = this.errors.toTransportErrors(),
    goodsList = goodsListResponse.toTransportError()
)

fun MutableList<Goods>.toTransportError(): List<GoodsResponseObjectDto>? = this
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Goods.toTransportError(): GoodsResponseObjectDto = GoodsResponseObjectDto(
    id = id.takeIf { it != GoodsId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    type = type.toTransportError(),
    price = price.toString(),
    weight = weight.takeIf { it.isNotBlank() }
)

private fun GoodsType.toTransportError(): GoodsTypeDto? = when (this) {
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
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CommonError.toTransportError() = ErrorDto(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)