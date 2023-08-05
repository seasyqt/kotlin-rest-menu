package ru.otuskotlin.learning.models.order

import ru.otuskotlin.learning.models.goods.GoodsId

data class GoodsOrder(
    var id: GoodsId = GoodsId.NONE,
    var name: String = "",
    var price: Int = 0,
    var count: Int = 0
)
