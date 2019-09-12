package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import apibuilder.slave.Slave
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateInformationBySsidItem: IInformation, DatabaseResponse() {
    lateinit var slave: Slave

    fun create(slave: Slave): UpdateInformationBySsidItem {
        this.slave = slave
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, slave.ssid)
                .put(SlaveInformation.DeviceImageHash.name, slave.deviceImageHash)
                .put(SlaveInformation.GoboImageHash.name, slave.goboImageHash)
                .put(SlaveInformation.Type.name, slave.type)
                .put(SlaveInformation.Device.name, slave.device)
                .put(SlaveInformation.Manufacturer.name, slave.manufacturer)
                .put(SlaveInformation.Rotating.name, slave.isRotating)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateInformationBySsidItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        slave = Slave().build(message = body)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateInformationBySsid,
                table = DatabaseType.Information
        )
    }
}