package apibuilder.slave.request.header

import org.json.JSONObject

class HeaderBuilder: IHeader, SlaveRequest()  {
    fun build(message: String): Header = parseToHeaderObject(message = message)
    override fun toJson(): JSONObject = throw Exception("Not implemented!")
}