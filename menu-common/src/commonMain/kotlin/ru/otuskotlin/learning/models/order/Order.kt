package ru.otuskotlin.learning.models.order

import kotlinx.datetime.Instant
import ru.otuskotlin.learning.utils.NONE
import java.math.BigInteger


data class Order(
    var id: OrderId = OrderId.NONE,
    var buyerId: BuyerId = BuyerId.NONE,
    var goodsList: MutableList<GoodsOrder> = mutableListOf(),
    var createDate: Instant = Instant.NONE,
    var totalPrice: BigInteger = BigInteger.ZERO

    )
