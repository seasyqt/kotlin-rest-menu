package ru.otus.otuskotlin.marketplace.biz.repo

import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == State.RUNNING }
    handle {
        goodsRepoRead = goodsValidated.copy()
        goodsRepoPrepare = goodsRepoRead

    }
}
