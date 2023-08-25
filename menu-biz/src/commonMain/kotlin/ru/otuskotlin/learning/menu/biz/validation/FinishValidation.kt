package ru.otus.otuskotlin.marketplace.biz.validation

import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.ICorChainDsl
import ru.otuskotlin.learning.menu.cor.worker

fun ICorChainDsl<GoodsContext>.finishGoodsValidation(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        goodsValidated = goodsValidating
    }
}

fun ICorChainDsl<GoodsContext>.finishGoodsFilterValidation(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        goodsFilterValidated = goodsFilterValidating
    }
}
