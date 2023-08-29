package ru.otus.otuskotlin.marketplace.biz.workers

import models.State
import models.goods.*
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*
import ru.otuskotlin.learning.stub.GoodsStubObject
import stubs.GoodsStub

fun ICorChainDsl<GoodsContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stub = GoodsStubObject.prepareResult {
            goodsRequest.id.takeIf { it != GoodsId.NONE }?.also { this.id = it }
            goodsRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            goodsRequest.type.takeIf { it != GoodsType.NONE }?.also { this.type = it }
            goodsRequest.price.takeIf { it != 0L }?.also { this.price = it }
            goodsRequest.weight.takeIf { it.isNotBlank() }?.also { this.weight = it }
        }
        goodsResponse = stub
    }
}
