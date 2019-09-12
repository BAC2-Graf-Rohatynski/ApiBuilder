package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import apibuilder.slave.Slave
import enumstorage.network.NetworkCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateInformationItem: INetworkItem, NetworkResponse() {
    lateinit var slave: Slave

    fun create(slave: Slave): UpdateInformationItem {
        this.slave = slave
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, slave.ssid)
                .put(SlaveInformation.Type.name, slave.type)
                .put(SlaveInformation.Device.name, slave.device)
                .put(SlaveInformation.Manufacturer.name, slave.manufacturer)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateInformationItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        slave = Slave().build(message = body)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.UpdateInformation)
    }
}