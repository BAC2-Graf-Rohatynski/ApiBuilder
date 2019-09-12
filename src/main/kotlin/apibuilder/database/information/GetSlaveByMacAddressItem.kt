package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class GetSlaveByMacAddressItem: IInformation, DatabaseResponse() {
    lateinit var macAddress: String

    fun create(macAddress: String): GetSlaveByMacAddressItem {
        this.macAddress = macAddress
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject().put(SlaveInformation.MacAddress.name, macAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GetSlaveByMacAddressItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        macAddress = body.getString(SlaveInformation.MacAddress.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetSlaveByMacAddress,
                table = DatabaseType.Information
        )
    }
}