package ru.otus.otuskotlin.marketplace.biz.workers

import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == State.NONE }
    handle { state = State.RUNNING }
}
