package apibuilder.slave.response.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}