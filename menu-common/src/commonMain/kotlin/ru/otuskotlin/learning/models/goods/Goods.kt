package ru.otuskotlin.learning.models.goods

import java.math.BigInteger

data class Goods(
    var id: GoodsId = GoodsId.NONE,
    var name: String = "",
    var type: GoodsType = GoodsType.NONE,
    var price: BigInteger = BigInteger.ZERO,
    var weight: String = ""
)
