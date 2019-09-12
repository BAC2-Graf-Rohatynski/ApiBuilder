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

class AddSlaveItem: IInformation, DatabaseResponse() {
    lateinit var slave: Slave

    fun create(slave: Slave): AddSlaveItem {
        this.slave = slave
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, slave.ssid)
                .put(SlaveInformation.MacAddress.name, slave.macAddress)
                .put(SlaveInformation.IpAddress.name, slave.ipAddress)
                .put(SlaveInformation.DeviceImageHash.name, slave.deviceImageHash)
                .put(SlaveInformation.GoboImageHash.name, slave.goboImageHash)
                .put(SlaveInformation.Status.name, slave.status)
                .put(SlaveInformation.Type.name, slave.type)
                .put(SlaveInformation.Device.name, slave.device)
                .put(SlaveInformation.Manufacturer.name, slave.manufacturer)
                .put(SlaveInformation.Rotating.name, slave.isRotating)
                .put(SlaveInformation.Timestamp.name, slave.timestamp)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): AddSlaveItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        slave = Slave().build(message = body)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.AddSlave,
                table = DatabaseType.Information
        )
    }
}