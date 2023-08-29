package ru.otus.otuskotlin.marketplace.biz.general


import models.DebugMode
import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { debugMode != DebugMode.STUB }
    handle {
        goodsResponse = goodsRepoDone
        goodsListResponse = goodsListRepoDone
        state = when (val st = state) {
            State.RUNNING -> State.FINISHING
            else -> st
        }
    }
}
