package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.common.helpers.errorValidation
import ru.otuskotlin.learning.menu.common.helpers.fail
import ru.otuskotlin.learning.menu.cor.ICorChainDsl
import ru.otuskotlin.learning.menu.cor.worker

fun ICorChainDsl<GoodsContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { goodsValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
