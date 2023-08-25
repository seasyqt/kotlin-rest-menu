package ru.otuskotlin.learning.menu.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.DebugMode
import models.State
import models.goods.*
import ru.otuskotlin.learning.menu.biz.*
import ru.otuskotlin.learning.menu.common.GoodsContext
import stubs.GoodsStub
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals



@OptIn(ExperimentalCoroutinesApi::class)
class GoodsUpdateStubTest {
    private val processor = GoodsProcessor()
    val id = GoodsId("777")
    val name = "Пицца Пеперони"
    val type = GoodsType.PIZZA
    val price = BigInteger.valueOf(100)
    val weight = "100 гр"

    @Test
    fun create() = runTest {

        val ctx = GoodsContext(
            command = GoodsCommand.UPDATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.SUCCESS,
            goodsRequest = Goods(
                id = id,
                name = name,
                type = type,
                price = price,
                weight = weight,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.goodsResponse.id)
        assertEquals(name, ctx.goodsResponse.name)
        assertEquals(type, ctx.goodsResponse.type)
        assertEquals(price, ctx.goodsResponse.price)
        assertEquals(weight, ctx.goodsResponse.weight)
    }

    @Test
    fun badId() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.UPDATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_ID,
            goodsRequest = Goods(),
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.UPDATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_PRICE,
            goodsRequest = Goods(
                id = id,
                name = "",
                type = type,
                price = price,
                weight = weight,
            ),
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("price", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.UPDATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_NAME,
            goodsRequest = Goods(
                id = id,
                name = name,
                type = type,
                price = price,
                weight = weight,
            ),
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("name", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun dbError() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.UPDATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.DB_ERROR,
            goodsRequest = Goods(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.UPDATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_SEARCH_STRING,
            goodsRequest = Goods(
                id = id,
                name = name,
                type = type,
                price = price,
                weight = weight,
            ),
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

}
