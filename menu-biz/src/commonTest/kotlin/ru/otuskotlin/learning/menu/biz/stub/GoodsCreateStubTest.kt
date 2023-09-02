package ru.otuskotlin.learning.menu.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import models.goods.*
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.stub.GoodsStubObject
import stubs.GoodsStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GoodsCreateStubTest {

    private val processor = GoodsProcessor(GoodsCorSettings())
    val id = GoodsId("123")
    val name = "Пицца Пеперони"
    val type = GoodsType.PIZZA
    val price = 100L
    val weight = "100"

    @Test
    fun create() = runTest {

        val ctx = GoodsContext(
            command = GoodsCommand.CREATE,
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
        assertEquals(GoodsStubObject.get().id, ctx.goodsResponse.id)
        assertEquals(name, ctx.goodsResponse.name)
        assertEquals(type, ctx.goodsResponse.type)
        assertEquals(price, ctx.goodsResponse.price)
        assertEquals(weight, ctx.goodsResponse.weight)
    }

    @Test
    fun badName() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.CREATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_NAME,
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
        assertEquals("name", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.CREATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_TYPE,
            goodsRequest = Goods(
                id = id,
                name = name,
                type = GoodsType.NONE,
                price = price,
                weight = weight,
            ),
        )
        processor.exec(ctx)
//        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("type", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun dbError() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.CREATE,
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
            command = GoodsCommand.CREATE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_ID,
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
