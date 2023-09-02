package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.goods.*
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock
import ru.otuskotlin.learning.menu.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoGoodsUpdateTest {
    abstract val repo: IGoodsRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = GoodsId("ad-repo-update-not-found")
    protected val lockBad = GoodsLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = GoodsLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        Goods(
            id = updateSucc.id,
            name = "update object",
            type = GoodsType.PIZZA,
            price = 100,
            weight = "update object weight",
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = Goods(
        id = updateIdNotFound,
        name = "update object not found",
        type = GoodsType.PIZZA,
        price = 100,
        weight = "update object not found weight",
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        Goods(
            id = updateConc.id,
            name = "update object not found",
            type = GoodsType.PIZZA,
            price = 100,
            weight = "update object not found weight",
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateGoods(DbGoodsRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.type, result.data?.type)
        assertEquals(reqUpdateSucc.weight, result.data?.weight)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateGoods(DbGoodsRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateGoods(DbGoodsRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<Goods> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
