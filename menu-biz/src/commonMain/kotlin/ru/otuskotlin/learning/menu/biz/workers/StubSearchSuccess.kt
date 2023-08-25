package ru.otus.otuskotlin.marketplace.biz.workers

import models.State
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*
import ru.otuskotlin.learning.stub.GoodsStubObject
import stubs.GoodsStub

fun ICorChainDsl<GoodsContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        goodsListResponse.addAll(GoodsStubObject.prepareSearchList(goodsFilterRequest.nameSearch, GoodsType.PIZZA))
    }
}
