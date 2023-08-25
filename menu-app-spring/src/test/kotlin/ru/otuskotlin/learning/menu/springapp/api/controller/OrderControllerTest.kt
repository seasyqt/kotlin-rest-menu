package ru.otuskotlin.learning.menu.springapp.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.otuskotlin.learning.api.v1.models.*
import ru.otuskotlin.learning.menu.common.OrderContext
import ru.otuskotlin.learning.menu.mappers.*
import ru.otuskotlin.learning.menu.springapp.service.OrderBlockingProcessor

@WebMvcTest(OrderController::class)
@Disabled
class OrderControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var processor: OrderBlockingProcessor


    @Test
    fun createOrder() = testStubGoods(
        "/v1/order/create",
        OrderCreateRequestDto(),
        OrderContext().toTransportCreate()
    )

    @Test
    fun readOrder() = testStubGoods(
        "/v1/order/read",
        OrderReadRequestDto(),
        OrderContext().toTransportRead()
    )

    @Test
    fun updateOrder() = testStubGoods(
        "/v1/order/update",
        OrderUpdateRequestDto(),
        OrderContext().toTransportUpdate()
    )

    @Test
    fun deleteOrder() = testStubGoods(
        "/v1/order/delete",
        OrderDeleteRequestDto(),
        OrderContext().toTransportDelete()
    )

    @Test
    fun searchOrder() = testStubGoods(
        "/v1/order/search",
        OrderSearchRequestDto(),
        OrderContext().toTransportSearch()
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