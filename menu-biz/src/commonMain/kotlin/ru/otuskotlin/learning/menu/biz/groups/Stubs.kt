package ru.otus.otuskotlin.marketplace.biz.groups

import models.DebugMode
import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.stubs(title: String, block: ICorChainDsl<GoodsContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { debugMode == DebugMode.STUB && state == State.RUNNING }
}
