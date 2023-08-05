package ru.otuskotlin.learning.models.order

import kotlinx.datetime.Instant

data class OrderFilter(
    var dateFrom: Instant = Instant.DISTANT_PAST,
    var dateTo: Instant = Instant.DISTANT_FUTURE,
    var buyerId: Int = 0
)
