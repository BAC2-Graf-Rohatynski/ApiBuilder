package apibuilder.database.image.item

import apibuilder.RsaModule
import apibuilder.database.interfaces.IItem
import enumstorage.image.Image
import org.json.JSONObject

class ImageItem: IItem {
    var id: Int = 0
    var isStandard: Boolean = false
    lateinit var imageDe: String
    lateinit var imageEn: String
    lateinit var fileStream: ByteArray
    lateinit var fileName: String

    fun build(id: Int, isStandard: Boolean, imageDe: String, imageEn: String, fileStream: ByteArray, fileName: String): ImageItem {
        this.id = id
        this.isStandard = isStandard
        this.imageDe = imageDe
        this.imageEn = imageEn
        this.fileStream = fileStream
        this.fileName = fileName
        return this
    }
    
    override fun toJson(): JSONObject = JSONObject()
            .put(Image.Id.name, id)
            .put(Image.ImageDe.name, imageDe)
            .put(Image.ImageEn.name, imageEn)
            .put(Image.IsStandard.name, isStandard)
            .put(Image.FileStream.name, fileStream.toString())
            .put(Image.FileName.name, fileName)

    override fun toObject(string: String): ImageItem {
        val apiMessage = RsaModule.decryptApiMessage(message = string).last() as JSONObject
        return toObject(json = apiMessage)
    }

    override fun toObject(json: JSONObject): ImageItem {
        id = if (json.has(Image.Id.name)) json.getInt(Image.Id.name) else 0
        imageDe = if (json.has(Image.ImageDe.name)) json.getString(Image.ImageDe.name) else String()
        imageEn = if (json.has(Image.ImageEn.name)) json.getString(Image.ImageEn.name) else String()
        isStandard = if (json.has(Image.IsStandard.name)) json.getBoolean(Image.IsStandard.name) else false
        fileStream = if (json.has(Image.FileStream.name)) json.getString(Image.FileStream.name).toByteArray() else ByteArray(0)
        fileName = if (json.has(Image.FileName.name)) json.getString(Image.FileName.name) else String()
        return this
    }
}