package ru.otuskotlin.learning.menu.repo.sql

import models.goods.Goods
import models.goods.GoodsId
import models.goods.GoodsType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock

object GoodsTable : Table("goods") {
    val id = varchar("id",128)
    val name = varchar("name", 128)
    val type = enumeration("type", GoodsType::class)
    val price = long("price")
    val weight = varchar("weight", 128)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = Goods(
        id = GoodsId(res[id].toString()),
        name = res[name],
        type = res[type],
        price = res[price],
        weight = res[weight],
        lock = GoodsLock(res[lock])
    )

    fun from(res: ResultRow) = Goods(
        id = GoodsId(res[id].toString()),
        name = res[name],
        type = res[type],
        price = res[price],
        weight = res[weight],
        lock = GoodsLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, goods: Goods, randomUuid: () -> String) {
        it[id] = goods.id.takeIf { it != GoodsId.NONE }?.asString() ?: randomUuid()
        it[name] = goods.name
        it[type] = goods.type
        it[price] = goods.price
        it[weight] = goods.weight
        it[lock] = goods.lock.takeIf { it != GoodsLock.NONE }?.asString() ?: randomUuid()
    }

}
