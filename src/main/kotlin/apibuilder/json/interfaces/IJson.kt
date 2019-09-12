package apibuilder.json.interfaces

import org.json.JSONArray
import org.json.JSONObject

interface IJson {
    fun replaceUnwantedChars(jsonArray: JSONArray): String = replaceUnwantedChars(stringObject = jsonArray.toString())
    fun replaceUnwantedChars(jsonObject: JSONObject): String = replaceUnwantedChars(stringObject = jsonObject.toString())
    fun replaceUnwantedChars(stringObject: String): String = stringObject
            .replace(" ", "")
            .replace("\\", "")
            .replace("[\"{", "[{")
            .replace("}\"]", "}]")
            .replace("}\",", "},")
            .replace(",\"{", ",{")
}