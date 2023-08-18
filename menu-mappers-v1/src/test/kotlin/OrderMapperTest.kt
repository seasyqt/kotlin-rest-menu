import kotlinx.datetime.Instant
import org.junit.Test
import ru.otuskotlin.learning.api.v1.models.*
import models.CommonError
import models.DebugMode
import models.RequestId
import models.State
import models.order.BuyerId
import models.order.GoodsOrder
import models.order.Order
import models.order.OrderCommand
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.mappers.*
import stubs.OrderStub
import java.math.BigInteger
import kotlin.test.assertEquals

class OrderMapperTest {
    @Test
    fun fromTransport() {
        val req = OrderCreateRequestDto(
            requestId = "1234",
            debug = OrderDebugDto(
                mode = RequestDebugModeDto.STUB,
                stub = OrderRequestDebugStubsDto.SUCCESS,
            ),
            order = OrderGoodsObjectDto(
                buyerId = "12123",
                goods = BaseGoodsOrderDto(
                    name = "Пицца",
                    price = "10000",
                    count = "40"
                )
            ),
        )

        val context = OrderContext()
        context.fromTransport(req)

        assertEquals(OrderStub.SUCCESS, context.stub)
        assertEquals(DebugMode.STUB, context.debugMode)
        assertEquals(BuyerId("12123"), context.orderRequest.buyerId)
        assertEquals("Пицца", context.orderRequest.goodsList[0].name)
        assertEquals(BigInteger.valueOf(40), context.orderRequest.goodsList[0].count)
        assertEquals(BigInteger.valueOf(10000), context.orderRequest.goodsList[0].price)
    }

    @Test
    fun toTransport() {
        val context = OrderContext(
            requestId = RequestId("1234"),
            command = OrderCommand.CREATE,
            orderResponse = Order(
                buyerId = BuyerId("456"),
                createDate = Instant.fromEpochMilliseconds(1000),
                totalPrice = BigInteger.valueOf(300),
                goodsList = createGoodsOrderList()
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

        val req = context.toTransport() as OrderCreateResponseDto

        assertEquals("1234", req.requestId)
        assertEquals("456", req.order?.buyerId)
        assertEquals("Паста", req.order?.goodsList?.get(0)?.name)
        assertEquals("560", req.order?.goodsList?.get(0)?.price)
        assertEquals("99", req.order?.goodsList?.get(0)?.count)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }

    private fun createGoodsOrderList(): MutableList<GoodsOrder> {
        return mutableListOf(
            GoodsOrder(
                name = "Паста",
                price = BigInteger.valueOf(560),
                count = BigInteger.valueOf(99)
            )
        )
    }
}