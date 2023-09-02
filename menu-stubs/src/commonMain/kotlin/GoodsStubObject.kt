package ru.otuskotlin.learning.stub

import models.goods.Goods
import models.goods.GoodsId
import models.goods.GoodsType
import ru.otuskotlin.learning.stub.GoodsStubPizza.GOODS_PIZZA

object GoodsStubObject {
    fun get(): Goods = GOODS_PIZZA.copy()

    fun prepareResult(block: Goods.() -> Unit): Goods = get().apply(block)

    fun prepareSearchList(name: String, type: GoodsType) = listOf(
        addGoods("1001", name, type, 100, "200 гр"),
        addGoods("1002", name, type, 200, "300 гр"),
        addGoods("1003", name, type, 300, "400 гр"),
        addGoods("1004", name, type, 150, "500 гр")

    )

    private fun addGoods(id: String, name: String, type: GoodsType, price: Long, weight: String) = prepareGoods(
        get(), id = id, name = name, type = type, price = price, weight = weight
    )

    private fun prepareGoods(base: Goods, id: String, name: String, type: GoodsType, price: Long, weight: String) =
        base.copy(
            id = GoodsId(id),
            name = name,
            type = type,
            price = price,
            weight = weight
        )
}