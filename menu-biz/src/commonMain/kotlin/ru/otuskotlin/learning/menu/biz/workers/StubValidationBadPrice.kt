package ru.otus.otuskotlin.marketplace.biz.workers

import models.CommonError
import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*
import stubs.GoodsStub

fun ICorChainDsl<GoodsContext>.stubValidationBadPrice(title: String) = worker {
    this.title = title
    on { stub == GoodsStub.BAD_PRICE && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            CommonError(
                group = "validation",
                code = "validation-price",
                field = "price",
                message = "Wrong price field"
            )
        )
    }
}
