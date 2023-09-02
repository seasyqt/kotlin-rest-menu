package ru.otuskotlin.learning.menu.common.repo

import models.goods.GoodsType

data class DbGoodsFilterRequest(
    var nameSearch: String = "",
    var type: GoodsType = GoodsType.NONE
)
