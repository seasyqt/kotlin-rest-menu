package ru.otuskotlin.learning.stub

import kotlinx.datetime.Instant
import models.goods.GoodsId
import models.order.BuyerId
import models.order.GoodsOrder
import models.order.Order
import models.order.OrderId
import java.math.BigInteger

object OrderStubPizza {

    val ORDER_SALAD: Order
        get() = Order(
            id = OrderId("123"),
            buyerId = BuyerId("333"),
            goodsList = mutableListOf(
                GoodsOrder(
                    id = GoodsId("55"),
                    name = "Салат",
                    price = BigInteger.valueOf(100),
                    count = BigInteger.TEN
                )
            ),
            createDate = Instant.DISTANT_FUTURE,
            totalPrice = BigInteger.valueOf(10000)
        )

}