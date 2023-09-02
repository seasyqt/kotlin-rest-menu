package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.otuskotlin.learning.menu.common.repo.*

class GoodsRepositoryMock(
    private val invokeCreateGoods: (DbGoodsRequest) -> DbGoodsResponse = { DbGoodsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadGoods: (DbGoodsIdRequest) -> DbGoodsResponse = { DbGoodsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateGoods: (DbGoodsRequest) -> DbGoodsResponse = { DbGoodsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteGoods: (DbGoodsIdRequest) -> DbGoodsResponse = { DbGoodsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchGoods: (DbGoodsFilterRequest) -> DbGoodsListResponse = { DbGoodsListResponse.MOCK_SUCCESS_EMPTY },
): IGoodsRepository {
    override suspend fun createGoods(rq: DbGoodsRequest): DbGoodsResponse {
        return invokeCreateGoods(rq)
    }

    override suspend fun readGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
        return invokeReadGoods(rq)
    }

    override suspend fun updateGoods(rq: DbGoodsRequest): DbGoodsResponse {
        return invokeUpdateGoods(rq)
    }

    override suspend fun deleteGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
        return invokeDeleteGoods(rq)
    }

    override suspend fun searchGoods(rq: DbGoodsFilterRequest): DbGoodsListResponse {
        return invokeSearchGoods(rq)
    }
}
