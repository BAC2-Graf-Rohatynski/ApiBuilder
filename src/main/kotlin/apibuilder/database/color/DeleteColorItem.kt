package apibuilder.database.color

import apibuilder.database.color.item.ColorItem
import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IColor
import enumstorage.color.Color
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray
import org.json.JSONObject

class DeleteColorItem: IColor, DatabaseResponse() {
    lateinit var item: ColorItem

    fun create(item: ColorItem): DeleteColorItem {
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

    override fun toObject(message: String): DeleteColorItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        item = ColorItem().build(
                id = body.getInt(Color.Id.name),
                colorDe = body.getString(Color.ColorDe.name),
                colorEn = body.getString(Color.ColorEn.name),
                hex = body.getString(Color.Hex.name),
                isStandard = body.getBoolean(Color.IsStandard.name)
        )
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.DeleteColor,
                table = DatabaseType.Color
        )
    }
}