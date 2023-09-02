package ru.otuskotlin.learning.repo.inmemory.model

import models.goods.Goods
import models.goods.GoodsId
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

data class GoodsEntity(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val price: Long? = null,
    val weight: String? = null,
    val lock: String? = null
    ) {
    constructor(model: Goods): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        type = model.type.takeIf { it != GoodsType.NONE }.toString(),
        price = model.price.takeIf { it != 0L },
        weight = model.weight.takeIf { it.isNotBlank() },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = Goods(
        id = id?.let { GoodsId(it) }?: GoodsId.NONE,
        name = name?: "",
        type = type?.let { GoodsType.valueOf(it) }?: GoodsType.NONE,
        price = price?: 0,
        weight = weight?: "",
        lock = lock?.let { GoodsLock(it) } ?: GoodsLock.NONE,
    )
}
