package apibuilder.database.color.item

import apibuilder.RsaModule
import apibuilder.database.interfaces.IItem
import enumstorage.color.Color
import org.json.JSONObject

class ColorItem: IItem {
    var id: Int = 0
    var isStandard: Boolean = false
    lateinit var colorDe: String
    lateinit var colorEn: String
    lateinit var hex: String

    fun build(id: Int, isStandard: Boolean, colorDe: String, colorEn: String, hex: String): ColorItem {
        this.id = id
        this.isStandard = isStandard
        this.colorDe = colorDe
        this.colorEn = colorEn
        this.hex = hex
        return this
    }

    override fun toJson(): JSONObject = JSONObject()
            .put(Color.Id.name, id)
            .put(Color.ColorDe.name, colorDe)
            .put(Color.ColorEn.name, colorEn)
            .put(Color.IsStandard.name, isStandard)
            .put(Color.Hex.name, hex)

    override fun toObject(string: String): ColorItem {
        val apiMessage = RsaModule.decryptApiMessage(message = string).last() as JSONObject
        return toObject(json = apiMessage)
    }

    override fun toObject(json: JSONObject): ColorItem {
        id = if (json.has(Color.Id.name)) json.getInt(Color.Id.name) else 0
        colorDe = if (json.has(Color.ColorDe.name)) json.getString(Color.ColorDe.name) else String()
        colorEn = if (json.has(Color.ColorEn.name)) json.getString(Color.ColorEn.name) else String()
        hex = if (json.has(Color.Hex.name)) json.getString(Color.Hex.name) else String()
        isStandard = if (json.has(Color.IsStandard.name)) json.getBoolean(Color.IsStandard.name) else false
        return this
    }
}