import org.junit.Test
import ru.otuskotlin.learning.api.v1.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {

    private val expectedRequest = GoodsCreateRequestDto(
        requestId = "123",
        requestType = "goodsCreate",
        debug = GoodsDebugDto(
            mode = RequestDebugModeDto.STUB,
            stub = GoodsRequestDebugStubsDto.BAD_ID
        ),
        goods = GoodsCreateObjectDto(
            name = "Goodsname",
            type = GoodsTypeDto.PIZZA,
            price = "100",
            weight = "100 гр",
        )
    )

    @Test
    fun serialize() {
        val json = apiJacksonRequestSerialize(expectedRequest)

        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"requestType\":\\s*\"goodsCreate\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badId\""))
        assertContains(json, Regex("\"name\":\\s*\"Goodsname\""))
        assertContains(json, Regex("\"type\":\\s*\"pizza\""))
        assertContains(json, Regex("\"price\":\\s*\"100\""))
        assertContains(json, Regex("\"weight\":\\s*\"100 гр\""))
    }

    @Test
    fun deserialize() {
        val json = apiJacksonRequestSerialize(expectedRequest)
        val obj = apiJacksonRequestDeserialize(json) as IRequestDto

        assertEquals(expectedRequest, obj)
    }

    @Test
    fun deserializeNaked() {
        val expectedRequestType = "goodsCreate"
        val jsonRequest = """
          {
          "requestType": "$expectedRequestType",
          "requestId": "123",
          "goods": {
            "name": "GoodsName",
            "type": "pizza",
            "price": 400,
            "weight": "100 гр"
          }
        }
        """.trimIndent()

        val goodsCreateRequest = apiJacksonRequestDeserialize(jsonRequest) as GoodsCreateRequestDto

        assertEquals(expectedRequestType, goodsCreateRequest.requestType)
    }

}