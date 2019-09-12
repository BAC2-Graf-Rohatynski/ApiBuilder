package apibuilder.database.interfaces

import org.json.JSONObject

interface IItem {
    fun toJson(): JSONObject
    fun toObject(json: JSONObject): IItem
    fun toObject(string: String): IItem
}