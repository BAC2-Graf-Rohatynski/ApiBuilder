package apibuilder.error.header

import apibuilder.RsaModule
import enumstorage.api.ApiValue
import org.json.JSONArray
import org.json.JSONObject

abstract class ErrorResponse {
    lateinit var header: Header

    fun parseToHeaderObject(message: String): Header {
        val apiMessage = RsaModule.decryptApiMessage(message = message)
        val header = apiMessage.first() as JSONObject

        return Header().build(
                command = header.getString(ApiValue.Command.name),
                requestId = header.getInt(ApiValue.RequestId.name),
                socketId = header.getInt(ApiValue.SocketId.name))
    }

    fun parseToHeaderObject(message: String, socketId: Int): Header {
        val apiMessage = RsaModule.decryptApiMessage(message = message)
        val header = apiMessage.first() as JSONObject

        return Header().build(
                command = header.getString(ApiValue.Command.name),
                requestId = header.getInt(ApiValue.RequestId.name),
                socketId = socketId)
    }

    fun parseToBodyObject(message: String): JSONArray =  RsaModule.decryptApiMessage(message = message)

    fun encryptApiMessage(message: JSONArray) = RsaModule.encryptApiMessage(message = message)
}