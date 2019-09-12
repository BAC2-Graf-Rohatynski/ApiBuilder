package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.database.DatabaseValue
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateSlaveBySsidItem: IInformation, DatabaseResponse() {
    lateinit var field: String
    lateinit var value: Any
    var ssid: Int = 0

    fun create(ssid: Int, field: String, value: Any): UpdateSlaveBySsidItem {
        this.field = field
        this.value = value
        this.ssid = ssid
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(DatabaseValue.Field.name, field)
                .put(DatabaseValue.Value.name, value)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateSlaveBySsidItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ssid = body.getInt(SlaveInformation.Ssid.name)
        field = body.getString(DatabaseValue.Field.name)
        value = body.get(DatabaseValue.Value.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateSlaveBySsid,
                table = DatabaseType.Information
        )
    }
}