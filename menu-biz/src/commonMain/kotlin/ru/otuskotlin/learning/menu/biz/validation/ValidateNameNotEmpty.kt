package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.common.helpers.errorValidation
import ru.otuskotlin.learning.menu.common.helpers.fail
import ru.otuskotlin.learning.menu.cor.ICorChainDsl
import ru.otuskotlin.learning.menu.cor.worker

fun ICorChainDsl<GoodsContext>.validateNameNotEmpty(title: String) = worker {
    this.title = title
    on { goodsValidating.name.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
