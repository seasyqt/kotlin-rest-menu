package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.goods.*
import ru.otuskotlin.learning.menu.common.models.goods.GoodsLock
import ru.otuskotlin.learning.menu.common.repo.DbGoodsRequest
import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoGoodsCreateTest {
    abstract val repo: IGoodsRepository

    protected open val lockNew: GoodsLock = GoodsLock("20000000-0000-0000-0000-000000000002")

    private val createObj = Goods(
        name = "create object",
        type = GoodsType.PIZZA,
        price = 100,
        weight = "create object weight"
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createGoods(DbGoodsRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: GoodsId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.name, result.data?.name)
        assertEquals(expected.type, result.data?.type)
        assertEquals(expected.price, result.data?.price)
        assertNotEquals(GoodsId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<Goods> = emptyList()
    }
}
