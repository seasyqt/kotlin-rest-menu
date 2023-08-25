package ru.otus.otuskotlin.marketplace.biz.workers

import models.CommonError
import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.cor.*
import stubs.GoodsStub

fun ICorChainDsl<GoodsContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.BAD_ID && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            CommonError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
