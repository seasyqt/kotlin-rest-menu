
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.common.repo.*
import ru.otuskotlin.learning.stub.GoodsStubObject

class GoodsRepoStub() : IGoodsRepository {
    override suspend fun createGoods(rq: DbGoodsRequest): DbGoodsResponse {
        return DbGoodsResponse(
            data = GoodsStubObject.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
        return DbGoodsResponse(
            data = GoodsStubObject.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateGoods(rq: DbGoodsRequest): DbGoodsResponse {
        return DbGoodsResponse(
            data = GoodsStubObject.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
        return DbGoodsResponse(
            data = GoodsStubObject.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchGoods(rq: DbGoodsFilterRequest): DbGoodsListResponse {
        return DbGoodsListResponse(
            data = GoodsStubObject.prepareSearchList("", GoodsType.SNACK),
            isSuccess = true,
        )
    }
}
