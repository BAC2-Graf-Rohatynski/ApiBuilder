package apibuilder.slave.request

import apibuilder.slave.Slave
import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.slave.SlaveCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateInformationSlaveItem: ISlaveRequestItem, SlaveRequest() {
    lateinit var slave: Slave

    fun create(slave: Slave): UpdateInformationSlaveItem {
        this.slave = slave
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Type.name, slave.type)
                .put(SlaveInformation.Device.name, slave.device)
                .put(SlaveInformation.Manufacturer.name, slave.manufacturer)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateInformationSlaveItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        slave = Slave().build(message = body)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.UpdateInformation)
    }
}