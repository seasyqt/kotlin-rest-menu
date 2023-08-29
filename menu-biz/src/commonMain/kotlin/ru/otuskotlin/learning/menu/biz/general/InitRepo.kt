package ru.otus.otuskotlin.marketplace.biz.general

import models.DebugMode
import ru.otuskotlin.learning.menu.common.*
import ru.otuskotlin.learning.menu.common.helpers.errorAdministration
import ru.otuskotlin.learning.menu.common.helpers.fail
import ru.otuskotlin.learning.menu.common.repo.IGoodsRepository
import ru.otuskotlin.learning.menu.cor.*

fun ICorChainDsl<GoodsContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        goodsRepo = when(debugMode.name) {
            DebugMode.TEST.name -> settings.repoTest
            DebugMode.STUB.name -> settings.repoStub
            else -> settings.repoProd
        }
        if (debugMode != DebugMode.STUB && goodsRepo == IGoodsRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen debugMode ($debugMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
