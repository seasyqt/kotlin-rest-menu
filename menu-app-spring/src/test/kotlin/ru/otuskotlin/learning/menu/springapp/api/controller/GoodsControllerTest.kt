package ru.otuskotlin.learning.menu.springapp.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.menu.common.GoodsContext
import ru.otuskotlin.learning.menu.mappers.*
import ru.otuskotlin.learning.menu.springapp.service.GoodsBlockingProcessor

@WebMvcTest(GoodsController::class)
class GoodsControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var processor: GoodsBlockingProcessor


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


    private fun <Req : Any, Res : Any> testStubGoods(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        val request = mapper.writeValueAsString(requestObj)
        val response = mapper.writeValueAsString(responseObj)

        mvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(response))
    }

}