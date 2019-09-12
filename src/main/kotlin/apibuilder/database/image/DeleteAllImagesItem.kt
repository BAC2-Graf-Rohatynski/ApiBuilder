package apibuilder.database.image

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IImage
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray

class DeleteAllImagesItem: IImage, DatabaseResponse() {
    fun create(): DeleteAllImagesItem {
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): DeleteAllImagesItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.DeleteAllImages,
                table = DatabaseType.Image
        )
    }
}