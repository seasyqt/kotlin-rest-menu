package ru.otus.otuskotlin.marketplace.biz.workers

import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*
import ru.otuskotlin.learning.stub.GoodsStubObject
import stubs.GoodsStub

fun ICorChainDsl<GoodsContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stub = GoodsStubObject.prepareResult {
            goodsRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
        }
        goodsResponse = stub
    }
}
