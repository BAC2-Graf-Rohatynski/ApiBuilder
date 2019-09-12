package apibuilder.slave.request.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}