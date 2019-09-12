package apibuilder.error.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}