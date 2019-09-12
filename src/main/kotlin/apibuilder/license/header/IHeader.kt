package apibuilder.license.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}