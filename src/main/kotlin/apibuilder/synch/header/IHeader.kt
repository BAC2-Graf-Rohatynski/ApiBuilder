package apibuilder.synch.header

import org.json.JSONObject

interface IHeader {
    fun toJson(): JSONObject
}