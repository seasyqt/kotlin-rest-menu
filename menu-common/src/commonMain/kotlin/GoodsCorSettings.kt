package ru.otuskotlin.learning.menu.common

import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository

data class GoodsCorSettings(
    val repoStub: IGoodsRepository = IGoodsRepository.NONE,
    val repoTest: IGoodsRepository = IGoodsRepository.NONE,
    val repoProd: IGoodsRepository = IGoodsRepository.NONE,
) {
    companion object {
        val NONE = GoodsCorSettings()
    }
}
