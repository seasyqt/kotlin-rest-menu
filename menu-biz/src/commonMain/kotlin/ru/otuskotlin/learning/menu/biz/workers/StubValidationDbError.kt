package ru.otus.otuskotlin.marketplace.biz.workers

import models.CommonError
import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.cor.*
import stubs.GoodsStub

fun ICorChainDsl<GoodsContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.DB_ERROR && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            CommonError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
