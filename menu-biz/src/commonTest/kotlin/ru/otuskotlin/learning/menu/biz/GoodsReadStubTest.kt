package ru.otus.otuskotlin.marketplace.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.DebugMode
import models.State
import models.goods.Goods
import models.goods.GoodsCommand
import models.goods.GoodsId
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.stub.GoodsStubObject
import stubs.GoodsStub
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class GoodsReadStubTest {

    private val processor = GoodsProcessor()
    val id = GoodsId("666")

    @Test
    fun read() = runTest {

        val ctx = GoodsContext(
            command = GoodsCommand.READ,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.SUCCESS,
            goodsRequest = Goods(
                id = id,
            ),
        )
        processor.exec(ctx)
        with(GoodsStubObject.get()) {
            assertEquals(id, ctx.goodsResponse.id)
            assertEquals(name, ctx.goodsResponse.name)
            assertEquals(type, ctx.goodsResponse.type)
            assertEquals(price, ctx.goodsResponse.price)
            assertEquals(weight, ctx.goodsResponse.weight)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.READ,
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
            command = GoodsCommand.READ,
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
    fun badName() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.READ,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_NAME,
            goodsRequest = Goods(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }


}
