package apibuilder.database.image

import apibuilder.database.image.item.ImageItem
import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IImage
import enumstorage.image.Image
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray
import org.json.JSONObject

class UpdateImageByIdItem: IImage, DatabaseResponse() {
    lateinit var item: ImageItem

    fun create(item: ImageItem): UpdateImageByIdItem {
        this.item = item
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = item.toJson()

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateImageByIdItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        item = ImageItem().build(
                id = body.getInt(Image.Id.name),
                imageDe = body.getString(Image.ImageDe.name),
                imageEn = body.getString(Image.ImageEn.name),
                isStandard = body.getBoolean(Image.IsStandard.name),
                fileStream = body.getString(Image.FileStream.name).toByteArray(),
                fileName = body.getString(Image.FileName.name)
        )
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateImageById,
                table = DatabaseType.Image
        )
    }
}