package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.database.DatabaseValue
import enumstorage.network.NetworkCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateSsidItem: INetworkItem, NetworkResponse() {
    lateinit var ipAddress: String
    var ssid: Int = 0
    var newSsid: Int = 0

    fun create(ssid: Int, newSsid: Int, ipAddress: String): UpdateSsidItem {
        this.ipAddress = ipAddress
        this.ssid = ssid
        this.newSsid = newSsid
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.IpAddress.name, ipAddress)
                .put(DatabaseValue.Value.name, newSsid)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateSsidItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        ssid = body.getInt(SlaveInformation.Ssid.name)
        newSsid = body.getInt(DatabaseValue.Value.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.UpdateSsid)
    }
}