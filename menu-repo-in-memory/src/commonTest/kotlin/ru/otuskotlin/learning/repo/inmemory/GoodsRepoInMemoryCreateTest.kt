package ru.otus.otuskotlin.marketplace.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoGoodsCreateTest
import ru.otuskotlin.learning.repo.inmemory.GoodsRepoInMemory


class GoodsRepoInMemoryCreateTest : RepoGoodsCreateTest() {
    override val repo = GoodsRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}