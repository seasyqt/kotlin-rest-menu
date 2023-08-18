import org.junit.Test
import ru.otuskotlin.learning.api.v1.models.*
import models.CommonError
import models.DebugMode
import models.RequestId
import models.State
import models.goods.Goods
import models.goods.GoodsCommand
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.mappers.*
import stubs.GoodsStub
import java.math.BigInteger
import kotlin.test.assertEquals

class GoodsMapperTest {
    @Test
    fun fromTransport() {
        val req = GoodsCreateRequestDto(
            requestId = "1234",
            debug = GoodsDebugDto(
                mode = RequestDebugModeDto.STUB,
                stub = GoodsRequestDebugStubsDto.SUCCESS,
            ),
            goods = GoodsCreateObjectDto(
                name = "Салат",
                type = GoodsTypeDto.SALAD,
                price = "500",
                weight = "600гр",
            ),
        )

        val context = GoodsContext()
        context.fromTransport(req)

        assertEquals(GoodsStub.SUCCESS, context.stub)
        assertEquals(DebugMode.STUB, context.debugMode)
        assertEquals("Салат", context.goodsRequest.name)
        assertEquals(GoodsType.SALAD, context.goodsRequest.type)
        assertEquals("600гр", context.goodsRequest.weight)
        assertEquals(BigInteger.valueOf(500), context.goodsRequest.price)
    }

    @Test
    fun toTransport() {
        val context = GoodsContext(
            requestId = RequestId("1234"),
            command = GoodsCommand.CREATE,
            goodsResponse = Goods(
                name = "Батончик",
                type = GoodsType.SNACK,
                price = BigInteger.valueOf(300),
                weight = "888",
            ),
            errors = mutableListOf(
                CommonError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = State.RUNNING,
        )

        val req = context.toTransport() as GoodsCreateResponseDto

        assertEquals("1234", req.requestId)
        assertEquals("Батончик", req.goods?.name)
        assertEquals(GoodsTypeDto.SNACK, req.goods?.type)
        assertEquals("300", req.goods?.price)
        assertEquals("888", req.goods?.weight)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}