import ru.otuskotlin.learning.api.v2.models.*

class TestUtils {

    companion object {
        val goodsCreateRequest: IRequestDto = GoodsCreateRequestDto(
            requestId = "123",
            requestType = "goodsCreate",
            debug = GoodsDebugDto(
                mode = RequestDebugModeDto.STUB,
                stub = GoodsRequestDebugStubsDto.SUCCESS
            ),
            goods = GoodsCreateObjectDto(
                name = "Пицца четыре сыр",
                type = GoodsTypeDto.PIZZA,
                price = "100",
                weight = "100 гр",
            )
        )

        val goodsReadRequest: IRequestDto = GoodsReadRequestDto(
            requestType = "goodsRead",
            requestId = "12313",
            debug = GoodsDebugDto(
                mode = RequestDebugModeDto.STUB,
                stub = GoodsRequestDebugStubsDto.SUCCESS
            ),
            id = "123123"
        )

        val goodsUpdateRequest: IRequestDto = GoodsUpdateRequestDto(
            requestType = "goodsUpdate",
            requestId = "313",
            debug = GoodsDebugDto(
                mode = RequestDebugModeDto.STUB,
                stub = GoodsRequestDebugStubsDto.SUCCESS
            ),
            goods = GoodsUpdateObjectDto(
                id = "123123",
                name = "Пицца Мясная ",
                type = GoodsTypeDto.PIZZA,
                weight = "400 гр",
                price = "1000"
            )
        )

        val goodsDeleteRequest: IRequestDto = GoodsDeleteRequestDto(
            requestType = "goodsDelete",
            requestId = "5312523",
            debug = GoodsDebugDto(
                mode = RequestDebugModeDto.STUB,
                stub = GoodsRequestDebugStubsDto.SUCCESS
            ),
            id = "123123"
        )

        val goodsCreateResponse = GoodsCreateResponseDto(
            requestId = "123",
            responseType = "goodsCreate",
            goods = GoodsResponseObjectDto(
                name = "Goodsname",
                type = GoodsTypeDto.PIZZA,
                price = "100",
                weight = "100 гр",
            )
        )
    }
}