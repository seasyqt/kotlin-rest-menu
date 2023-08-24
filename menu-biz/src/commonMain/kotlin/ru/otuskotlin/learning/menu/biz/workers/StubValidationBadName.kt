package ru.otus.otuskotlin.marketplace.biz.workers

import models.CommonError
import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*
import stubs.GoodsStub

fun ICorChainDsl<GoodsContext>.stubValidationBadName(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.BAD_NAME && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            CommonError(
                group = "validation",
                code = "validation-name",
                field = "name",
                message = "Wrong name field"
            )
        )
    }
}
