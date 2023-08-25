package ru.otuskotlin.learning.menu.springapp.api.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import io.mockk.coVerify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.menu.biz.GoodsProcessor
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.mappers.*
import ru.otuskotlin.learning.menu.springapp.config.CorConfig

@WebFluxTest(GoodsController::class, CorConfig::class)
internal class GoodsControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: GoodsProcessor


    @Test
    fun createGoods() = testStubGoods(
        "/v1/goods/create",
        GoodsCreateRequestDto(),
        GoodsContext().toTransportCreate()
    )

    @Test
    fun readGoods() = testStubGoods(
        "/v1/goods/read",
        GoodsReadRequestDto(),
        GoodsContext().toTransportRead()
    )

    @Test
    fun updateGoods() = testStubGoods(
        "/v1/goods/update",
        GoodsUpdateRequestDto(),
        GoodsContext().toTransportUpdate()
    )

    @Test
    fun deleteGoods() = testStubGoods(
        "/v1/goods/delete",
        GoodsDeleteRequestDto(),
        GoodsContext().toTransportDelete()
    )

    @Test
    fun searchGoods() = testStubGoods(
        "/v1/goods/search",
        GoodsSearchRequestDto(),
        GoodsContext().toTransportSearch()
    )


    private inline fun <reified Req : Any, reified Res : IResponseDto> testStubGoods(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {

        coEvery { processor.process<Res>(any(), any(), any()) } returns responseObj


        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                Assertions.assertThat(it).isEqualTo(responseObj)
            }
        coVerify { processor.process<Res>(any(), any(), any()) }
    }

}