package ru.otuskotlin.learning.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import models.CommonError
import models.goods.Goods
import models.goods.GoodsId
import models.goods.GoodsType
import ru.otuskotlin.learning.menu.common.helpers.errorRepoConcurrency
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock
import ru.otuskotlin.learning.menu.common.repo.*
import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository
import ru.otuskotlin.learning.repo.inmemory.model.GoodsEntity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class GoodsRepoInMemory(
    initObjects: List<Goods> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IGoodsRepository {

    private val cache = Cache.Builder<String, GoodsEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(goods: Goods) {
        val entity = GoodsEntity(goods)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createGoods(rq: DbGoodsRequest): DbGoodsResponse {
        val key = randomUuid()
        val goods = rq.goods.copy(id = GoodsId(key), lock = GoodsLock(randomUuid()))
        val entity = GoodsEntity(goods)
        cache.put(key, entity)
        return DbGoodsResponse(
            data = goods,
            isSuccess = true,
        )
    }

    override suspend fun readGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
        val key = rq.id.takeIf { it != GoodsId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbGoodsResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateGoods(rq: DbGoodsRequest): DbGoodsResponse {
        val key = rq.goods.id.takeIf { it != GoodsId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.goods.lock.takeIf { it != GoodsLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newGoods = rq.goods.copy(lock = GoodsLock(randomUuid()))
        val entity = GoodsEntity(newGoods)
        return mutex.withLock {
            val oldGoods = cache.get(key)
            when {
                oldGoods == null -> resultErrorNotFound
                oldGoods.lock != oldLock -> DbGoodsResponse(
                    data = oldGoods.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(GoodsLock(oldLock), oldGoods.lock?.let { GoodsLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbGoodsResponse(
                        data = newGoods,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteGoods(rq: DbGoodsIdRequest): DbGoodsResponse {
        val key = rq.id.takeIf { it != GoodsId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != GoodsLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldGoods = cache.get(key)
            when {
                oldGoods == null -> resultErrorNotFound
                oldGoods.lock != oldLock -> DbGoodsResponse(
                    data = oldGoods.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(GoodsLock(oldLock), oldGoods.lock?.let { GoodsLock(it) }))
                )

                else -> {
                    cache.invalidate(key)
                    DbGoodsResponse(
                        data = oldGoods.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchGoods(rq: DbGoodsFilterRequest): DbGoodsListResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.type.takeIf { it != GoodsType.NONE }?.let {
                    it.name == entry.value.type
                } ?: true
            }
            .filter { entry ->
                rq.nameSearch.takeIf { it.isNotBlank() }?.let {
                    entry.value.name?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbGoodsListResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbGoodsResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CommonError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbGoodsResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CommonError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbGoodsResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                CommonError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
