package apibuilder.network.header

import org.json.JSONObject

class HeaderBuilder: IHeader, NetworkResponse()  {
    fun build(message: String): Header = parseToHeaderObject(message = message)
    override fun toJson(): JSONObject = throw Exception("Not implemented!")
}