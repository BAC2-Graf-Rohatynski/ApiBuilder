package apibuilder.error.response

import apibuilder.RsaModule
import apibuilder.error.header.ErrorResponse
import apibuilder.error.header.Header
import apibuilder.error.interfaces.IErrorItem
import enumstorage.api.ApiValue
import org.json.JSONArray
import org.json.JSONObject

class ResponseItem: IErrorItem, ErrorResponse() {
    private lateinit var headerObject: JSONObject
    lateinit var messageBody: JSONObject
    var value: Any? = null
    var isResponse: Boolean = false
    var isGetMessage: Boolean = false
    var socketId: Int = 0

    fun create(message: String, value: Any? = null, isResponse: Boolean = false, isGetMessage: Boolean = false, socketId: Int): ResponseItem {
        val apiMessage = RsaModule.decryptApiMessage(message = message)
        headerObject = apiMessage.first() as JSONObject
        messageBody = apiMessage.last() as JSONObject
        this.value = value
        this.isResponse = isResponse
        this.isGetMessage = isGetMessage
        this.socketId = socketId
        buildHeader()
        return this
    }

    fun getOriginalMessage(): JSONArray = JSONArray()
            .put(header)
            .put(messageBody)

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(ApiValue.IsGetMessage.name, isGetMessage)
                .put(ApiValue.IsResponse.name, isResponse)
                .put(ApiValue.Response.name, value ?: String())

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(messageBody)
                .put(body))
    }

    override fun toObject(message: String): ResponseItem {
        header = parseToHeaderObject(message = message)

        val apiMessage = RsaModule.decryptApiMessage(message = message)
        messageBody = apiMessage[1] as JSONObject

        val body = apiMessage.last() as JSONObject
        value = body.get(ApiValue.Response.name)
        isResponse = body.getBoolean(ApiValue.IsResponse.name)
        isGetMessage = body.getBoolean(ApiValue.IsGetMessage.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = headerObject.getString(ApiValue.Command.name),
                requestId = headerObject.getInt(ApiValue.RequestId.name))
    }
}