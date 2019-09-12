package apibuilder.network.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}