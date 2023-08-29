package ru.otus.otuskotlin.marketplace.biz.repo

import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == State.RUNNING }
    handle {
        goodsRepoPrepare = goodsValidated.copy()
    }
}
