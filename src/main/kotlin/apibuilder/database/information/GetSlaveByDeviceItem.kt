package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class GetSlaveByDeviceItem: IInformation, DatabaseResponse() {
    lateinit var device: String

    fun create(device: String): GetSlaveByDeviceItem {
        this.device = device
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject().put(SlaveInformation.Device.name, device)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GetSlaveByDeviceItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        device = body.getString(SlaveInformation.Device.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetSlaveByDevice,
                table = DatabaseType.Information
        )
    }
}