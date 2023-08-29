package ru.otuskotlin.learning.menu.common.repo

import models.goods.Goods


data class DbGoodsRequest(
    val goods: Goods
)
