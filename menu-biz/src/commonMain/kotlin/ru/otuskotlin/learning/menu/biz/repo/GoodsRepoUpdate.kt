package ru.otus.otuskotlin.marketplace.biz.repo

import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.common.repo.DbGoodsRequest
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.repoUpdate(title: String) = worker {
    this.title = title
    this.description = "Обновление продукта в БД"
    on { state == State.RUNNING }
    handle {
        val request = DbGoodsRequest(goodsRepoPrepare)
        val result = goodsRepo.updateGoods(request)
        val resultGoods = result.data
        if (result.isSuccess && resultGoods != null) {
            goodsRepoDone = resultGoods
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
            goodsRepoDone
        }
    }
}
