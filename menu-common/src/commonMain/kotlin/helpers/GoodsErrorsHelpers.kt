package ru.otuskotlin.learning.menu.common.helpers

import models.CommonError
import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext

fun Throwable.asGoodsError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = CommonError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun GoodsContext.addError(vararg error: CommonError) = errors.addAll(error)

fun GoodsContext.fail(error: CommonError) {
    addError(error)
    state = State.FAILING
}

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: CommonError.Level = CommonError.Level.ERROR,
) = CommonError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)
