package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.DebugMode
import models.State
import models.goods.GoodsCommand
import models.goods.GoodsFilter
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = GoodsCommand.SEARCH
    private val processor by lazy { GoodsProcessor() }

    @Test
    fun correctEmpty() = runTest {
        val ctx = GoodsContext(
            command = command,
            state = State.NONE,
            debugMode = DebugMode.TEST,
            goodsFilterRequest = GoodsFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(State.FAILING, ctx.state)
    }
}

