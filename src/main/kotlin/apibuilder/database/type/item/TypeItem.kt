package apibuilder.database.type.item

import apibuilder.RsaModule
import apibuilder.database.interfaces.IItem
import enumstorage.type.Type
import org.json.JSONObject

class TypeItem: IItem {
    var id: Int = 0
    var isStandard: Boolean = false
    lateinit var typeDe: String
    lateinit var typeEn: String

    fun build(id: Int, isStandard: Boolean, typeDe: String, typeEn: String): TypeItem {
        this.id = id
        this.isStandard = isStandard
        this.typeDe = typeDe
        this.typeEn = typeEn
        return this
    }
    
    override fun toJson(): JSONObject = JSONObject()
            .put(Type.Id.name, id)
            .put(Type.TypeDe.name, typeDe)
            .put(Type.TypeEn.name, typeEn)
            .put(Type.IsStandard.name, isStandard)

    override fun toObject(string: String): TypeItem {
        val apiMessage = RsaModule.decryptApiMessage(message = string).last() as JSONObject
        return toObject(json = apiMessage)
    }

    override fun toObject(json: JSONObject): TypeItem {
        id = if (json.has(Type.Id.name)) json.getInt(Type.Id.name) else 0
        typeDe = if (json.has(Type.TypeDe.name)) json.getString(Type.TypeDe.name) else String()
        typeEn = if (json.has(Type.TypeEn.name)) json.getString(Type.TypeEn.name) else String()
        isStandard = if (json.has(Type.IsStandard.name)) json.getBoolean(Type.IsStandard.name) else false
        return this
    }
}