package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import models.goods.*
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsContext
import java.math.BigInteger
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = GoodsId("123-234-abc-ABC"),
            name = "abc",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = ""
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = GoodsId(" \n\t 123-234-abc-ABC \n\t "),
            name = "abc",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = ""
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = GoodsId(""),
            name = "abc",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = ""
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = GoodsId("!@#\$%^&*(),.{}"),
            name = "abc",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = ""
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
