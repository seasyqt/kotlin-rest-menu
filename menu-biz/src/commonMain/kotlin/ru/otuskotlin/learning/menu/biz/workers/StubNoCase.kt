package ru.otus.otuskotlin.marketplace.biz.workers

import models.CommonError
import models.State
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.common.helpers.fail
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        fail(
            CommonError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stub.name}"
            )
        )
    }
}
