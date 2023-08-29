package ru.otus.otuskotlin.marketplace.biz.repo

import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.common.repo.*
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение продукта из БД"
    on { state == State.RUNNING }
    handle {
        val request = DbGoodsIdRequest(goodsValidated)
        val result = goodsRepo.readGoods(request)
        val resultGoods = result.data
        if (result.isSuccess && resultGoods != null) {
            goodsRepoRead = resultGoods
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}
