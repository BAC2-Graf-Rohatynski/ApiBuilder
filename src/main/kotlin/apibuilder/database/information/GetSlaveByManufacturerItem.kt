package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IDatabaseItem
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class GetSlaveByManufacturerItem: IDatabaseItem, DatabaseResponse() {
    lateinit var manufacturer: String

    fun create(manufacturer: String): GetSlaveByManufacturerItem {
        this.manufacturer = manufacturer
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject().put(SlaveInformation.Manufacturer.name, manufacturer)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GetSlaveByManufacturerItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        manufacturer = body.getString(SlaveInformation.Manufacturer.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetSlaveByManufacturer,
                table = DatabaseType.Information
        )
    }
}