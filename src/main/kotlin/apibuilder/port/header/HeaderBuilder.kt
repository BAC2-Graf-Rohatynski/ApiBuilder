package apibuilder.port.header

import org.json.JSONObject

class HeaderBuilder: IHeader, PortResponse()  {
    fun build(message: String): Header = parseToHeaderObject(message = message)
    override fun toJson(): JSONObject = throw Exception("Not implemented!")
}