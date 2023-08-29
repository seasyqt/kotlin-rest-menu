package ru.otuskotlin.learning.menu.common.helpers

import models.CommonError
import models.State
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.common.exceptions.RepoConcurrencyException
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

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

fun errorAdministration(
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: CommonError.Level = CommonError.Level.ERROR,
) = CommonError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)

fun errorRepoConcurrency(
    expectedLock: GoodsLock,
    actualLock: GoodsLock?,
    exception: Exception? = null,
) = CommonError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = CommonError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = CommonError(
    field = "id",
    message = "Id must not be null or blank"
)
