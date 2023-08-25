package ru.otus.otuskotlin.marketplace.biz.groups

import models.State
import models.goods.GoodsCommand
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.operation(
    title: String,
    command: GoodsCommand,
    block: ICorChainDsl<GoodsContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == State.RUNNING }
}
