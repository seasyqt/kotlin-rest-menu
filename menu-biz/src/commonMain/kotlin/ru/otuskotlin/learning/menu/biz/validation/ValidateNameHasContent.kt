package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.common.helpers.errorValidation
import ru.otuskotlin.learning.menu.common.helpers.fail
import ru.otuskotlin.learning.menu.cor.ICorChainDsl
import ru.otuskotlin.learning.menu.cor.worker

fun ICorChainDsl<GoodsContext>.validateNameHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { goodsValidating.name.isNotEmpty() && !goodsValidating.name.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
