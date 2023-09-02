package ru.otus.otuskotlin.marketplace.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoGoodsSearchTest
import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository
import ru.otuskotlin.learning.repo.inmemory.GoodsRepoInMemory

class GoodsRepoInMemorySearchTest : RepoGoodsSearchTest() {
    override val repo: IGoodsRepository = GoodsRepoInMemory(
        initObjects = initObjects
    )
}
