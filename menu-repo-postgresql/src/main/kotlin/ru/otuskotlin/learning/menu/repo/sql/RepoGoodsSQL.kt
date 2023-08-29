package ru.otuskotlin.learning.menu.repo.sql

import com.benasher44.uuid.uuid4
import models.goods.Goods
import models.goods.GoodsId
import models.goods.GoodsType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otuskotlin.learning.menu.common.helpers.asGoodsError
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock
import ru.otuskotlin.learning.menu.common.repo.*

class RepoGoodsSQL(
    properties: SqlProperties,
    initObjects: Collection<Goods> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IGoodsRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) {
                SchemaUtils.drop(GoodsTable)
            }
            SchemaUtils.create(GoodsTable)
            initObjects.forEach { createGoods(it) }
        }
    }

    private fun createGoods(goods: Goods): Goods {
        val res = GoodsTable.insert {
            to(it, goods, randomUuid)
        }

        return GoodsTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbGoodsResponse): DbGoodsResponse =
        transactionWrapper(block) { DbGoodsResponse.error(it.asGoodsError()) }

    override suspend fun createGoods(rq: DbGoodsRequest): DbGoodsResponse = transactionWrapper {
        DbGoodsResponse.success(createGoods(rq.goods))
    }

    private fun read(id: GoodsId): DbGoodsResponse {
        val res = GoodsTable.select {
            GoodsTable.id eq id.asString()
        }.singleOrNull() ?: return DbGoodsResponse.errorNotFound
        return DbGoodsResponse.success(GoodsTable.from(res))
    }

    override suspend fun readGoods(rq: DbGoodsIdRequest): DbGoodsResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: GoodsId,
        lock: GoodsLock,
        block: (Goods) -> DbGoodsResponse
    ): DbGoodsResponse =
        transactionWrapper {
            if (id == GoodsId.NONE) return@transactionWrapper DbGoodsResponse.errorEmptyId

            val current = GoodsTable.select { GoodsTable.id eq id.asString() }
                .firstOrNull()
                ?.let { GoodsTable.from(it) }

            when {
                current == null -> DbGoodsResponse.errorNotFound
                current.lock != lock -> DbGoodsResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateGoods(rq: DbGoodsRequest): DbGoodsResponse =
        update(rq.goods.id, rq.goods.lock) {
            GoodsTable.update({ GoodsTable.id eq rq.goods.id.asString() }) {
                to(it, rq.goods, randomUuid)
            }
            read(rq.goods.id)
        }

    override suspend fun deleteGoods(rq: DbGoodsIdRequest): DbGoodsResponse = update(rq.id, rq.lock) {
        GoodsTable.deleteWhere { id eq rq.id.asString() }
        DbGoodsResponse.success(it)
    }

    override suspend fun searchGoods(rq: DbGoodsFilterRequest): DbGoodsListResponse =
        transactionWrapper({
            val res = GoodsTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.type != GoodsType.NONE) {
                        add(GoodsTable.type eq rq.type)
                    }
                    if (rq.nameSearch.isNotBlank()) {
                        add(
                            (GoodsTable.name like "%${rq.nameSearch}%")
                                    or (GoodsTable.name like "%${rq.nameSearch}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbGoodsListResponse(data = res.map { GoodsTable.from(it) }, isSuccess = true)
        }, {
            DbGoodsListResponse.error(it.asGoodsError())
        })
}
