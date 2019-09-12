package apibuilder.port.header

import apibuilder.RsaModule
import apibuilder.port.interfaces.IPortItem
import enumstorage.api.ApiValue
import org.json.JSONArray
import org.json.JSONObject

abstract class PortResponse {
    lateinit var header: Header

    fun parseToHeaderObject(message: String): Header {
        val apiMessage = RsaModule.decryptApiMessage(message = message)
        val header = apiMessage.first() as JSONObject
        return Header().build(command = header.getString(ApiValue.Command.name))
    }

    fun parseToBodyObject(message: String): JSONArray =  RsaModule.decryptApiMessage(message = message)

    fun encryptApiMessage(message: JSONArray) = RsaModule.encryptApiMessage(message = message)
}