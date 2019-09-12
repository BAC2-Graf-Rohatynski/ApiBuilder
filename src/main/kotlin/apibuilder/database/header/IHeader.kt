package apibuilder.database.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}