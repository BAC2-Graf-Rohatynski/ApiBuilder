package apibuilder.port.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}