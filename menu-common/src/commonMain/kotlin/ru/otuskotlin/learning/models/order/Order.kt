package ru.otuskotlin.learning.models.order


data class Order(
    var id: OrderId = OrderId.NONE,
    var goodsList: MutableList<GoodsOrder> = mutableListOf()

    )
