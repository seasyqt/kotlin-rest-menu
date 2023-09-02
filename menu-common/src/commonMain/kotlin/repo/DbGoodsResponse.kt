package ru.otuskotlin.learning.menu.common.repo

import models.CommonError
import models.goods.Goods
import ru.otuskotlin.learning.menu.common.helpers.*
import ru.otuskotlin.learning.menu.common.helpers.errorEmptyId as goodsErrorEmptyId
import ru.otuskotlin.learning.menu.common.helpers.errorNotFound as goodsErrorNotFound
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

data class DbGoodsResponse(
    override val data: Goods?,
    override val isSuccess: Boolean,
    override val errors: List<CommonError> = emptyList()
) : IDbResponse<Goods> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbGoodsResponse(null, true)
        fun success(result: Goods) = DbGoodsResponse(result, true)
        fun error(errors: List<CommonError>, data: Goods? = null) = DbGoodsResponse(data, false, errors)
        fun error(error: CommonError, data: Goods? = null) = DbGoodsResponse(data, false, listOf(error))

        val errorEmptyId = error(goodsErrorEmptyId)

        fun errorConcurrent(lock: GoodsLock, goods: Goods?) = error(
            errorRepoConcurrency(lock, goods?.lock?.let { GoodsLock(it.asString()) }),
            goods
        )

        val errorNotFound = error(goodsErrorNotFound)
    }
}
