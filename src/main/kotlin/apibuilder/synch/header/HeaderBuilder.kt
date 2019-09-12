package apibuilder.synch.header

import org.json.JSONObject

class HeaderBuilder: IHeader, SynchResponse()  {
    fun getHeader(message: String): Header = parseToHeaderObject(message = message)
    override fun toJson(): JSONObject = throw Exception("Not implemented!")
}