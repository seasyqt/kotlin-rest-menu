import org.junit.Test
import ru.otuskotlin.learning.api.v1.models.*
import kotlin.test.*

class ResponseSerializationTest {


    private val expectedResponse = GoodsCreateResponseDto(
        requestId = "123",
        responseType = "goodsCreate",
        goods = GoodsResponseObjectDto(
            name = "Goodsname",
            type = GoodsTypeDto.PIZZA,
            price = "100",
            weight = "100 гр",
        )
    )

    @Test
    fun serialize() {
        val jsonResponse = apiJacksonResponseSerialize(expectedResponse)

        assertContains(jsonResponse, Regex("\"requestId\":\\s*\"123\""))
        assertContains(jsonResponse, Regex("\"responseType\":\\s*\"goodsCreate\""))
        assertContains(jsonResponse, Regex("\"name\":\\s*\"Goodsname\""))
        assertContains(jsonResponse, Regex("\"type\":\\s*\"pizza\""))
        assertContains(jsonResponse, Regex("\"price\":\\s*\"100\""))
        assertContains(jsonResponse, Regex("\"weight\":\\s*\"100 гр\""))
    }

    @Test
    fun deserialize() {
        val json = apiJacksonResponseSerialize(expectedResponse)
        val obj = apiJacksonResponseDeserialize(json) as GoodsCreateResponseDto

        assertEquals(expectedResponse, obj)
    }

    @Test
    fun deserializeNaked() {
        val expectedResponseType = "goodsCreate"
        val jsonString = """
         {
          "responseType": "$expectedResponseType",
          "requestId": "123",
          "goods": {
            "name": "GoodsName",
            "type": "pizza",
            "price": 400,
            "weight": "100 гр"
          }
        }
        """.trimIndent()
        val goodsCreateResponse = apiJacksonResponseDeserialize(jsonString) as GoodsCreateResponseDto

        assertEquals(expectedResponseType, goodsCreateResponse.responseType)
    }

}