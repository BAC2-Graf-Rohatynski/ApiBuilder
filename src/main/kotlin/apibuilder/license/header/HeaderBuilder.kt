package apibuilder.license.header

import org.json.JSONObject

class HeaderBuilder: IHeader, LicenseResponse()  {
    fun build(message: String): Header = parseToHeaderObject(message = message)
    override fun toJson(): JSONObject = throw Exception("Not implemented!")
}