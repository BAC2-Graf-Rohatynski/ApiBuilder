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

class UpdateSlaveByMacAddressItem: IInformation, DatabaseResponse() {
    lateinit var field: String
    lateinit var value: Any
    lateinit var macAddress: String

    fun create(macAddress: String, field: String, value: Any): UpdateSlaveByMacAddressItem {
        this.field = field
        this.value = value
        this.macAddress = macAddress
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.MacAddress.name, macAddress)
                .put(DatabaseValue.Field.name, field)
                .put(DatabaseValue.Value.name, value)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateSlaveByMacAddressItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        macAddress = body.getString(SlaveInformation.MacAddress.name)
        field = body.getString(DatabaseValue.Field.name)
        value = body.get(DatabaseValue.Value.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateSlaveByMacAddress,
                table = DatabaseType.Information
        )
    }
}