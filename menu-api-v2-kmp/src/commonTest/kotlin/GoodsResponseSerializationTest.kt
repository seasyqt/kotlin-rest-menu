import TestUtils.Companion.goodsCreateResponse
import org.junit.Test
import ru.otuskotlin.learning.api.v2.apiKmpResponseDeserialize
import ru.otuskotlin.learning.api.v2.apiKmpResponseSerialize
import ru.otuskotlin.learning.api.v2.models.*
import kotlin.test.*

class GoodsResponseSerializationTest {


    @Test
    fun serialize() {
        val jsonResponse = apiKmpResponseSerialize(goodsCreateResponse)

        assertContains(jsonResponse, Regex("\"requestId\":\\s*\"123\""))
        assertContains(jsonResponse, Regex("\"responseType\":\\s*\"goodsCreate\""))
        assertContains(jsonResponse, Regex("\"name\":\\s*\"Goodsname\""))
        assertContains(jsonResponse, Regex("\"type\":\\s*\"pizza\""))
        assertContains(jsonResponse, Regex("\"price\":\\s*\"100\""))
        assertContains(jsonResponse, Regex("\"weight\":\\s*\"100 гр\""))
    }

    @Test
    fun deserialize() {
        val json = apiKmpResponseSerialize(goodsCreateResponse)
        val obj = apiKmpResponseDeserialize(json) as GoodsCreateResponseDto

        assertEquals(goodsCreateResponse, obj)
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
            "price": "400",
            "weight": "100 гр"
          }
        }
        """.trimIndent()
        val goodsCreateResponse = apiKmpResponseDeserialize(jsonString) as GoodsCreateResponseDto

        assertEquals(expectedResponseType, goodsCreateResponse.responseType)
    }

}