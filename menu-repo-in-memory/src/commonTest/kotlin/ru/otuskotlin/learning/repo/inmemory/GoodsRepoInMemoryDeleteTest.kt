package ru.otus.otuskotlin.marketplace.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.*
import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository
import ru.otuskotlin.learning.repo.inmemory.*

class GoodsRepoInMemoryDeleteTest : RepoGoodsDeleteTest() {
    override val repo: IGoodsRepository = GoodsRepoInMemory(
        initObjects = initObjects
    )
}
