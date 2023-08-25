package ru.otuskotlin.learning.menu.biz

import kotlinx.datetime.Clock
import models.goods.GoodsCommand
import models.goods.GoodsId
import ru.otus.otuskotlin.marketplace.biz.groups.*
import ru.otus.otuskotlin.marketplace.biz.validation.finishGoodsValidation
import ru.otus.otuskotlin.marketplace.biz.validation.*
import ru.otus.otuskotlin.marketplace.biz.validation.validation
import ru.otus.otuskotlin.marketplace.biz.workers.*
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.common.helpers.asGoodsError
import ru.otuskotlin.learning.menu.common.helpers.fail
import ru.otuskotlin.learning.menu.cor.*


class GoodsProcessor {
    suspend fun exec(context: GoodsContext) = BusinessChain.exec(context)

    suspend fun <T> process(
        command: GoodsCommand,
        fromTransport: suspend (GoodsContext) -> Unit,
        sendResponse: suspend (GoodsContext) -> T): T {

        val ctx = GoodsContext(
            startTime = Clock.System.now(),
        )
        var realCommand = command

        return try {
                fromTransport(ctx)
                realCommand = ctx.command

                exec(ctx)
                 sendResponse(ctx)

        } catch (e: Throwable) {
                ctx.command = realCommand
                ctx.fail(e.asGoodsError())
                exec(ctx)
                sendResponse(ctx)

        }
    }

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
                validation {
                    worker("Копируем поля в goodsValidating") { goodsValidating = goodsRequest.copy() }
                    worker("Очистка id") { goodsValidating.id = GoodsId.NONE }
                    worker("Очистка названия") { goodsValidating.name = goodsValidating.name.trim() }
                    worker("Очистка веса товара") { goodsValidating.weight = goodsValidating.weight.trim() }
                    validateNameNotEmpty("Проверка, что название продукта не пустое")
                    validateNameHasContent("Проверка символов")

                    finishGoodsValidation("Завершение проверок")
                }
            }
            operation("Получить продукт", GoodsCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в goodsValidating") { goodsValidating = goodsRequest.copy() }
                    worker("Очистка id") { goodsValidating.id = GoodsId(goodsValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishGoodsValidation("Успешное завершение процедуры валидации")
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
                validation {
                    worker("Копируем поля в goodsValidating") { goodsValidating = goodsRequest.copy() }
                    worker("Очистка id") { goodsValidating.id = GoodsId(goodsValidating.id.asString().trim()) }
                    worker("Очистка названия") { goodsValidating.name = goodsValidating.name.trim() }
                    worker("Очистка веса товара") { goodsValidating.weight = goodsValidating.weight.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateNameNotEmpty("Проверка на непустое название продукта")
                    validateNameHasContent("Проверка на наличие содержания в названии продукта")

                    finishGoodsValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Удалить продукт", GoodsCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в goodsValidating") {
                        goodsValidating = goodsRequest.copy() }
                    worker("Очистка id") { goodsValidating.id = GoodsId(goodsValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishGoodsValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск продукта", GoodsCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { goodsFilterValidating = goodsFilterRequest.copy() }

                    finishGoodsFilterValidation("Успешное завершение процедуры валидации")
                }

            }

        }.build()
    }
}
