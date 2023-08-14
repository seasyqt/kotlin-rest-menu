package ru.otuskotlin.learning.models.order

import ru.otuskotlin.learning.models.goods.GoodsId
import java.math.BigInteger

data class GoodsOrder(
    var id: GoodsId = GoodsId.NONE,
    var name: String = "",
    var price: BigInteger = BigInteger.ZERO,
    var count: BigInteger = BigInteger.ZERO
)
