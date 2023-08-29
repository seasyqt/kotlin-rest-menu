package ru.otuskotlin.learning.menu.common.repo

import models.CommonError
import models.goods.Goods

data class DbGoodsListResponse(
    override val data: List<Goods>?,
    override val isSuccess: Boolean,
    override val errors: List<CommonError> = emptyList(),
) : IDbResponse<List<Goods>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbGoodsListResponse(emptyList(), true)
        fun success(result: List<Goods>) = DbGoodsListResponse(result, true)
        fun error(errors: List<CommonError>) = DbGoodsListResponse(null, false, errors)
        fun error(error: CommonError) = DbGoodsListResponse(null, false, listOf(error))
    }
}
