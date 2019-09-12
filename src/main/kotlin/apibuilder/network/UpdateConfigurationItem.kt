package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.network.NetworkCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateConfigurationItem: INetworkItem, NetworkResponse() {
    lateinit var ipAddress: String
    lateinit var ddfHash: String
    lateinit var ddfFile: String
    var ssid: Int = 0

    fun create(ddfFile: String, ssid: Int, ddfHash: String, ipAddress: String): UpdateConfigurationItem {
        this.ipAddress = ipAddress
        this.ddfHash = ddfHash
        this.ddfFile = ddfFile
        this.ssid = ssid
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.DdfFile.name, ddfFile)
                .put(SlaveInformation.DdfHash.name, ddfHash)
                .put(SlaveInformation.IpAddress.name, ipAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateConfigurationItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ddfHash = body.getString(SlaveInformation.DdfHash.name)
        ddfFile = body.getString(SlaveInformation.DdfFile.name)
        ssid = body.getInt(SlaveInformation.Ssid.name)
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.UpdateConfiguration)
    }
}