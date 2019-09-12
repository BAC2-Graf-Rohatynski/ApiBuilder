package apibuilder.error.header

import org.json.JSONObject

class HeaderBuilder: IHeader, ErrorResponse() {
    fun build(message: String, socketId: Int): Header = parseToHeaderObject(message = message, socketId = socketId)
    fun build(message: String): Header = parseToHeaderObject(message = message)
    override fun toJson(): JSONObject = throw Exception("Not implemented!")
}