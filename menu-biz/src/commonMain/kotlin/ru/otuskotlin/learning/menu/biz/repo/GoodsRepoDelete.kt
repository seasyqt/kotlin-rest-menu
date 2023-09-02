package ru.otus.otuskotlin.marketplace.biz.repo

import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.common.repo.DbGoodsIdRequest
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление продукта из БД по ID"
    on { state == State.RUNNING }
    handle {
        val request = DbGoodsIdRequest(goodsRepoPrepare)
        val result = goodsRepo.deleteGoods(request)
        if (!result.isSuccess) {
            state = State.FAILING
            errors.addAll(result.errors)
        }
        goodsRepoDone = goodsRepoRead
    }
}
