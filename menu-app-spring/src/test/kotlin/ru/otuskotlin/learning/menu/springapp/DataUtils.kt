package ru.otuskotlin.learning.menu.springapp

import ru.otuskotlin.learning.api.v1.models.*

class DataUtils {
    companion object {
        fun goodsSearchResponse() = GoodsSearchResponseDto(
            responseType = "goodsSearch"
        )

        fun goodsCreateResponse() = GoodsCreateResponseDto(
            responseType = "goodsCreate"
        )

        fun goodsReadResponse() = GoodsReadResponseDto(
            responseType = "goodsRead"
        )

        fun goodsUpdateResponse() = GoodsUpdateResponseDto(
            responseType = "goodsUpdate"
        )

        fun goodsDeleteResponse() = GoodsDeleteResponseDto(
            responseType = "goodsDelete"
        )
    }

}