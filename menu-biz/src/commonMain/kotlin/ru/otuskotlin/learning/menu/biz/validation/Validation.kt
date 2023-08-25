package ru.otus.otuskotlin.marketplace.biz.validation

import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.ICorChainDsl
import ru.otuskotlin.learning.menu.cor.chain

fun ICorChainDsl<GoodsContext>.validation(block: ICorChainDsl<GoodsContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == State.RUNNING }
}
