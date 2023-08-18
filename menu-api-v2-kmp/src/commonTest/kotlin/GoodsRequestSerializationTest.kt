import TestUtils.Companion.goodsCreateRequest
import org.junit.Test
import ru.otuskotlin.learning.api.v2.apiKmpRequestDeserialize
import ru.otuskotlin.learning.api.v2.apiKmpRequestSerialize
import ru.otuskotlin.learning.api.v2.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class GoodsRequestSerializationTest {

    @Test
    fun serialize() {
        val json = apiKmpRequestSerialize(goodsCreateRequest)

        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"requestType\":\\s*\"goodsCreate\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"name\":\\s*\"Пицца четыре сыр\""))
        assertContains(json, Regex("\"type\":\\s*\"pizza\""))
        assertContains(json, Regex("\"price\":\\s*\"100\""))
        assertContains(json, Regex("\"weight\":\\s*\"100 гр\""))
    }

    @Test
    fun deserialize() {
        val json = apiKmpRequestSerialize(goodsCreateRequest)
        val obj = apiKmpRequestDeserialize(json) as IRequestDto

        assertEquals(goodsCreateRequest, obj)
    }

    @Test
    fun deserializeNaked() {
        val expectedRequestType = "goodsCreate"
        val jsonRequest = """
          {
          "requestType": "$expectedRequestType",
          "requestId": "123",
          "goods": {
            "name": "Пицца четыре сыр",
            "type": "pizza",
            "price": "400",
            "weight": "100 гр"
          }
        }
        """.trimIndent()

        val goodsCreateRequest = apiKmpRequestDeserialize(jsonRequest) as GoodsCreateRequestDto

        assertEquals(expectedRequestType, goodsCreateRequest.requestType)
    }

}