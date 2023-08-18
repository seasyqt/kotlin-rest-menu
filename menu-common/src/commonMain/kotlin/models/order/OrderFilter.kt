package models.order

import kotlinx.datetime.Instant

data class OrderFilter(
    var dateFrom: Instant = Instant.DISTANT_PAST,
    var dateTo: Instant = Instant.DISTANT_FUTURE,
    var buyerId: BuyerId = BuyerId.NONE
)
