package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.database.DatabaseValue
import org.json.JSONArray
import org.json.JSONObject

class UpdateSlaveAllWhereItem: IInformation, DatabaseResponse() {
    lateinit var setField: String
    lateinit var setValue: Any
    lateinit var whereField: String
    lateinit var whereValue: Any

    fun create(setField: String, setValue: Any, whereField: String, whereValue: Any): UpdateSlaveAllWhereItem {
        this.setField = setField
        this.setValue = setValue
        this.whereField = whereField
        this.whereValue = whereValue
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(DatabaseValue.WhereField.name, whereField)
                .put(DatabaseValue.WhereValue.name, whereValue)
                .put(DatabaseValue.SetField.name, setField)
                .put(DatabaseValue.SetValue.name, setValue)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateSlaveAllWhereItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        setField = body.getString(DatabaseValue.SetField.name)
        setValue = body.getString(DatabaseValue.SetValue.name)
        whereField = body.getString(DatabaseValue.WhereField.name)
        whereValue = body.getString(DatabaseValue.WhereValue.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateAllSlavesWhere,
                table = DatabaseType.Information
        )
    }
}