package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.goods.*
import ru.otuskotlin.learning.menu.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoGoodsSearchTest {
    abstract val repo: IGoodsRepository

    protected open val initializedObjects: List<Goods> = initObjects

    @Test
    fun searchType() = runRepoTest {
        val result = repo.searchGoods(DbGoodsFilterRequest(type = GoodsType.PIZZA))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitAds("search") {

        val searchOwnerId = GoodsId("owner-124")
        override val initObjects: List<Goods> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2"),
            createInitTestModel("ad3", type = GoodsType.PIZZA),
            createInitTestModel("ad4"),
            createInitTestModel(
                "ad5", type = GoodsType.PIZZA
            )
        )
    }
}
