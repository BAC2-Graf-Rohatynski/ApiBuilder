package apibuilder.database.gobo.item

import apibuilder.RsaModule
import apibuilder.database.interfaces.IItem
import enumstorage.gobo.Gobo
import org.json.JSONObject

class GoboItem: IItem {
    var id: Int = 0
    var isStandard: Boolean = false
    lateinit var goboDe: String
    lateinit var goboEn: String
    lateinit var fileStream: ByteArray
    lateinit var fileName: String

    fun build(id: Int, isStandard: Boolean, goboDe: String, goboEn: String, fileStream: ByteArray, fileName: String): GoboItem {
        this.id = id
        this.isStandard = isStandard
        this.goboDe = goboDe
        this.goboEn = goboEn
        this.fileStream = fileStream
        this.fileName = fileName
        return this
    }
    
    override fun toJson(): JSONObject = JSONObject()
            .put(Gobo.Id.name, id)
            .put(Gobo.GoboDe.name, goboDe)
            .put(Gobo.GoboEn.name, goboEn)
            .put(Gobo.IsStandard.name, isStandard)
            .put(Gobo.FileStream.name, fileStream.toString())
            .put(Gobo.FileName.name, fileName)

    override fun toObject(string: String): GoboItem {
        val apiMessage = RsaModule.decryptApiMessage(message = string).last() as JSONObject
        return toObject(json = apiMessage)
    }

    override fun toObject(json: JSONObject): GoboItem {
        id = if (json.has(Gobo.Id.name)) json.getInt(Gobo.Id.name) else 0
        goboDe = if (json.has(Gobo.GoboDe.name)) json.getString(Gobo.GoboDe.name) else String()
        goboEn = if (json.has(Gobo.GoboEn.name)) json.getString(Gobo.GoboEn.name) else String()
        isStandard = if (json.has(Gobo.IsStandard.name)) json.getBoolean(Gobo.IsStandard.name) else false
        fileStream = if (json.has(Gobo.FileStream.name)) json.getString(Gobo.FileStream.name).toByteArray() else ByteArray(0)
        fileName = if (json.has(Gobo.FileName.name)) json.getString(Gobo.FileName.name) else String()
        return this
    }
}