package ru.otuskotlin.learning.menu.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import models.goods.*
import models.goods.GoodsFilter
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.stub.GoodsStubObject
import stubs.GoodsStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail


@OptIn(ExperimentalCoroutinesApi::class)
class GoodsSearchStubTest {

    private val processor = GoodsProcessor(GoodsCorSettings())
    val filter = GoodsFilter(nameSearch = "bolt")

    @Test
    fun read() = runTest {

        val ctx = GoodsContext(
            command = GoodsCommand.SEARCH,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.SUCCESS,
            goodsFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.goodsListResponse.size > 1)
        val first = ctx.goodsListResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.name.contains(filter.nameSearch))
        with(GoodsStubObject.get()) {
            assertEquals(type, first.type)
            assertEquals(price, first.price)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.SEARCH,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_ID,
            goodsFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun dbError() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.SEARCH,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.DB_ERROR,
            goodsFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badName() = runTest {
        val ctx = GoodsContext(
            command = GoodsCommand.SEARCH,
            state = State.NONE,
            debugMode = DebugMode.STUB,
            stub = GoodsStub.BAD_NAME,
            goodsFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Goods(), ctx.goodsResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }

}
