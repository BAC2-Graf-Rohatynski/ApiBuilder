package apibuilder.database.manufacturer.item

import apibuilder.RsaModule
import apibuilder.database.interfaces.IItem
import enumstorage.manufacturer.Manufacturer
import org.json.JSONObject

class ManufacturerItem: IItem {
    var id: Int = 0
    var isStandard: Boolean = false
    lateinit var manufacturerDe: String
    lateinit var manufacturerEn: String

    fun build(id: Int, isStandard: Boolean, manufacturerDe: String, manufacturerEn: String): ManufacturerItem {
        this.id = id
        this.isStandard = isStandard
        this.manufacturerDe = manufacturerDe
        this.manufacturerEn = manufacturerEn
        return this
    }
    
    override fun toJson(): JSONObject = JSONObject()
            .put(Manufacturer.Id.name, id)
            .put(Manufacturer.ManufacturerDe.name, manufacturerDe)
            .put(Manufacturer.ManufacturerEn.name, manufacturerEn)
            .put(Manufacturer.IsStandard.name, isStandard)

    override fun toObject(string: String): ManufacturerItem {
        val apiMessage = RsaModule.decryptApiMessage(message = string).last() as JSONObject
        return toObject(json = apiMessage)
    }

    override fun toObject(json: JSONObject): ManufacturerItem {
        id = if (json.has(Manufacturer.Id.name)) json.getInt(Manufacturer.Id.name) else 0
        manufacturerDe = if (json.has(Manufacturer.ManufacturerDe.name)) json.getString(Manufacturer.ManufacturerDe.name) else String()
        manufacturerEn = if (json.has(Manufacturer.ManufacturerEn.name)) json.getString(Manufacturer.ManufacturerEn.name) else String()
        isStandard = if (json.has(Manufacturer.IsStandard.name)) json.getBoolean(Manufacturer.IsStandard.name) else false
        return this
    }
}