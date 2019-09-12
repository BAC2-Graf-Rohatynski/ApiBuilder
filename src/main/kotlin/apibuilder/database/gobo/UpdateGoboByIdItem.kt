package apibuilder.database.gobo

import apibuilder.database.gobo.item.GoboItem
import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IConfiguration
import enumstorage.gobo.Gobo
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray
import org.json.JSONObject

class UpdateGoboByIdItem: IConfiguration, DatabaseResponse() {
    lateinit var item: GoboItem

    fun create(item: GoboItem): UpdateGoboByIdItem {
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

    override fun toObject(message: String): UpdateGoboByIdItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        item = GoboItem().build(
                id = body.getInt(Gobo.Id.name),
                goboDe = body.getString(Gobo.GoboDe.name),
                goboEn = body.getString(Gobo.GoboEn.name),
                isStandard = body.getBoolean(Gobo.IsStandard.name),
                fileStream = body.getString(Gobo.FileStream.name).toByteArray(),
                fileName = body.getString(Gobo.FileName.name)
        )
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateGoboById,
                table = DatabaseType.Gobo
        )
    }
}