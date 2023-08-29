package ru.otus.otuskotlin.marketplace.biz.repo

import models.*
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.common.repo.*
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск продукта в БД по фильтру"
    on { state == State.RUNNING }
    handle {
        val request = DbGoodsFilterRequest(
            nameSearch = goodsFilterValidated.nameSearch,
            type = goodsFilterValidated.type
        )
        val result = goodsRepo.searchGoods(request)
        val resultGoodsList = result.data
        if (result.isSuccess && resultGoodsList != null) {
            goodsListRepoDone = resultGoodsList.toMutableList()
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}
