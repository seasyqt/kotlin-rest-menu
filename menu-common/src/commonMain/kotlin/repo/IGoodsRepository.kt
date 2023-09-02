package ru.otuskotlin.learning.menu.common.repo

interface IGoodsRepository {
    suspend fun createGoods(rq: DbGoodsRequest): DbGoodsResponse
    suspend fun readGoods(rq: DbGoodsIdRequest): DbGoodsResponse
    suspend fun updateGoods(rq: DbGoodsRequest): DbGoodsResponse
    suspend fun deleteGoods(rq: DbGoodsIdRequest): DbGoodsResponse
    suspend fun searchGoods(rq: DbGoodsFilterRequest): DbGoodsListResponse

    companion object {
        val NONE = object : IGoodsRepository {
            override suspend fun createGoods(rq: DbGoodsRequest): DbGoodsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateGoods(rq: DbGoodsRequest): DbGoodsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchGoods(rq: DbGoodsFilterRequest): DbGoodsListResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
