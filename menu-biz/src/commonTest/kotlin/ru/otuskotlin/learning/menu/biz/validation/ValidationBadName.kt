package ru.otus.otuskotlin.marketplace.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.DebugMode
import models.State
import models.goods.Goods
import models.goods.GoodsCommand
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.stub.GoodsStubObject
import java.math.BigInteger
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = GoodsStubObject.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameCorrect(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = stub.id,
            name = "name",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = "weight"
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
    assertEquals("name", ctx.goodsValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameTrim(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = stub.id,
            name = " \n\tname \n\t",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = "weight"
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
    assertEquals("name", ctx.goodsValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameEmpty(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = stub.id,
            name = "",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = "weight"
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameSymbols(command: GoodsCommand, processor: GoodsProcessor) = runTest {
    val ctx = GoodsContext(
        command = command,
        state = State.NONE,
        debugMode = DebugMode.TEST,
        goodsRequest = Goods(
            id = stub.id,
            name = "!@#\$%^&*(),.{}",
            type = GoodsType.PIZZA,
            price = BigInteger.TEN,
            weight = "weight",
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}
