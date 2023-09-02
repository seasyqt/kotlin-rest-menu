package ru.otus.otuskotlin.marketplace.biz.repo

import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == State.RUNNING }
    handle {
        goodsRepoPrepare = goodsRepoRead.copy().apply {
            this.name = goodsValidated.name
            type = goodsValidated.type
            price = goodsValidated.price
            weight = goodsValidated.weight
        }
    }
}
