package ru.otuskotlin.learning.menu.biz

import models.goods.GoodsCommand
import ru.otus.otuskotlin.marketplace.biz.groups.*
import ru.otus.otuskotlin.marketplace.biz.workers.*
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.cor.*


class GoodsProcessor {
    suspend fun exec(context: GoodsContext) = BusinessChain.exec(context)


    companion object {
        private val BusinessChain = rootChain<GoodsContext> {
            initStatus("Инициализация статуса")

            operation("Создание продукта", GoodsCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadPrice("Имитация ошибки валидации описания")
                    stubValidationBadType("Имитация ошибки валидации типа продукта")
                    stubValidationBadName("Имитация ошибки валидации названия")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Получить продукт", GoodsCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Изменить продукт", GoodsCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadName("Имитация ошибки валидации названия")
                    stubValidationBadType("Имитация ошибки валидации заголовка")
                    stubValidationBadPrice("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Удалить продукт", GoodsCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Поиск продукта", GoodsCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

            }

        }.build()
    }
}
