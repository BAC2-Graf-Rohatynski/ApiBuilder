package apibuilder.network.response

import apibuilder.RsaModule
import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.api.ApiValue
import org.json.JSONArray
import org.json.JSONObject

class ResponseItem: INetworkItem, NetworkResponse() {
    private lateinit var headerObject: JSONObject
    lateinit var messageBody: JSONObject
    var value: Any? = null

    fun create(message: String, value: Any? = null): ResponseItem {
        val apiMessage = RsaModule.decryptApiMessage(message = message)
        headerObject = apiMessage.first() as JSONObject
        messageBody = apiMessage.last() as JSONObject
        this.value = value
        buildHeader()
        return this
    }

    fun getOriginalMessage(): JSONArray = JSONArray()
            .put(header)
            .put(messageBody)

    override fun toJson(): String {
        val body = JSONObject().put(ApiValue.Response.name, value ?: String())

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): ResponseItem = throw Exception("Not implemented!")

    override fun buildHeader() {
        this.header = Header().build(command = headerObject.getString(ApiValue.Command.name))
    }
}