package models.goods

import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

data class Goods(
    var id: GoodsId = GoodsId.NONE,
    var name: String = "",
    var type: GoodsType = GoodsType.NONE,
    var price: Long = 0,
    var weight: String = "",
    var lock: GoodsLock = GoodsLock.NONE
)
