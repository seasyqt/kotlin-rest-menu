package ru.otus.otuskotlin.marketplace.biz.workers

import models.*
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.cor.*
import stubs.*
import ru.otuskotlin.learning.stub.*
import java.math.BigInteger

fun ICorChainDsl<GoodsContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stub = GoodsStubObject.prepareResult {
            goodsRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            goodsRequest.type.takeIf { it != GoodsType.NONE }?.also { this.type = it }
            goodsRequest.price.takeIf { it != BigInteger.ZERO }?.also { this.price = it }
            goodsRequest.weight.takeIf { it.isNotBlank() }?.also { this.weight = it }
        }
        goodsResponse = stub
    }
}
