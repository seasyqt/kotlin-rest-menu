package ru.otuskotlin.learning.menu.repo.sql

import ru.otus.otuskotlin.marketplace.backend.repo.tests.*
import ru.otuskotlin.learning.menu.common.repo.*

class RepoGoodsSQLCreateTest : RepoGoodsCreateTest() {
    override val repo: IGoodsRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoGoodsSQLDeleteTest : RepoGoodsDeleteTest() {
    override val repo: IGoodsRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoGoodsSQLReadTest : RepoGoodsReadTest() {
    override val repo: IGoodsRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoGoodsSQLSearchTest : RepoGoodsSearchTest() {
    override val repo: IGoodsRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoGoodsSQLUpdateTest : RepoGoodsUpdateTest() {
    override val repo: IGoodsRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
