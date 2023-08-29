package ru.otus.otuskotlin.marketplace.backend.repo.tests

import models.goods.*
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

abstract class BaseInitAds(val op: String): IInitObjects<Goods> {

    open val lockOld: GoodsLock = GoodsLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: GoodsLock = GoodsLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        price: Long = 100,
        type: GoodsType = GoodsType.SNACK,
        lock: GoodsLock = lockOld,
    ) = Goods(
        id = GoodsId("goods-repo-$op-$suf"),
        name = "$suf stub",
        type = type,
        price = price,
        weight = "$suf stub weight",
        lock = lock,
    )
}
