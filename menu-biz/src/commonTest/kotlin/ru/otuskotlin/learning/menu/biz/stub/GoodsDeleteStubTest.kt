package ru.otuskotlin.learning.menu.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.DebugMode
import models.State
import models.goods.Goods
import models.goods.GoodsCommand
import models.goods.GoodsId
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.common.GoodsCorSettings
import ru.otuskotlin.learning.stub.GoodsStubObject
import stubs.GoodsStub
import kotlin.test.Test
import kotlin.test.assertEquals
@OptIn(ExperimentalCoroutinesApi::class)
class GoodsDeleteStubTest {
    private val processor = GoodsProcessor(GoodsCorSettings())
    val id = GoodsId("666")

    @Test
    fun delete() = runTest {

        val ctx = GoodsContext(
            command = GoodsCommand.DELETE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.SUCCESS,
            goodsRequest = Goods(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = GoodsStubObject.get()
        assertEquals(stub.id, ctx.goodsResponse.id)
        assertEquals(stub.name, ctx.goodsResponse.name)
        assertEquals(stub.type, ctx.goodsResponse.type)
        assertEquals(stub.price, ctx.goodsResponse.price)
        assertEquals(stub.weight, ctx.goodsResponse.weight)
    }

    @Test
    fun badId() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.DELETE,
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
    fun dbError() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.DELETE,
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
    fun cannotDelete() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.DELETE,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.CANNOT_DELETE,
            goodsRequest = Goods(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }

}
