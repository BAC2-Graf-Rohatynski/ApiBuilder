package apibuilder.database.show

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IDdf
import enumstorage.api.ApiValue
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.database.DatabaseValue
import org.json.JSONArray
import org.json.JSONObject

class RenameShowItem: IDdf, DatabaseResponse() {
    lateinit var show: String
    lateinit var value: String

    fun create(show: String, value: String): RenameShowItem {
        this.show = show
        this.value = value
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(ApiValue.Show.name, show)
                .put(DatabaseValue.Value.name, value)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): RenameShowItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        show = body.getString(ApiValue.Show.name)
        value = body.getString(DatabaseValue.Value.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.RenameShow,
                table = DatabaseType.DdfDatabaseStorage
        )
    }
}