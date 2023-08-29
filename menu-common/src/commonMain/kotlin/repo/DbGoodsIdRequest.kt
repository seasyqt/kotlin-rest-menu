package ru.otuskotlin.learning.menu.common.repo

import models.goods.Goods
import models.goods.GoodsId
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

data class DbGoodsIdRequest(
    val id: GoodsId,
    val lock: GoodsLock = GoodsLock.NONE,
) {
    constructor(goods: Goods) : this(goods.id, goods.lock)
}
