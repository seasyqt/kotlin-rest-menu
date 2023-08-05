package ru.otuskotlin.learning.models.goods

data class Goods(
    var id: GoodsId = GoodsId.NONE,
    var name: String = "",
    var type: GoodsType = GoodsType.NONE,
    var price: Int = 0,
    var weight: String = ""
)
