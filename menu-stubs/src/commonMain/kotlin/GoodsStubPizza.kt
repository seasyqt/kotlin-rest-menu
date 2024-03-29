package ru.otuskotlin.learning.stub

import models.goods.Goods
import models.goods.GoodsId
import models.goods.GoodsType

object GoodsStubPizza {

    val GOODS_PIZZA: Goods
        get() = Goods(
            id = GoodsId("123"),
            name = "Пицца Пеперони",
            type = GoodsType.PIZZA,
            price = 100,
            weight = "100"
        )

    val GOODS_SNACK = GOODS_PIZZA.copy(type = GoodsType.SNACK)
}