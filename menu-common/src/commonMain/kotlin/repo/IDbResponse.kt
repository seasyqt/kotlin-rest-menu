package ru.otuskotlin.learning.menu.common.repo

import models.CommonError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<CommonError>
}
