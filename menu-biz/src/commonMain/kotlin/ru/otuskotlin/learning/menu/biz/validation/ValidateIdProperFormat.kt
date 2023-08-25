package ru.otus.otuskotlin.marketplace.biz.validation

import models.goods.GoodsId
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.common.helpers.errorValidation
import ru.otuskotlin.learning.menu.common.helpers.fail
import ru.otuskotlin.learning.menu.cor.ICorChainDsl
import ru.otuskotlin.learning.menu.cor.worker

fun ICorChainDsl<GoodsContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { goodsValidating.id != GoodsId.NONE && !goodsValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = goodsValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}
