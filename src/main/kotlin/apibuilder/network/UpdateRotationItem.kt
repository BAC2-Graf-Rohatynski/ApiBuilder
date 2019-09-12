package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.network.NetworkCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateRotationItem: INetworkItem, NetworkResponse() {
    lateinit var macAddress: String
    lateinit var ipAddress: String
    var ssid: Int = 0
    var isRotating: Boolean = false

    fun create(ssid: Int, isRotating: Boolean, macAddress: String, ipAddress: String): UpdateRotationItem {
        this.macAddress = macAddress
        this.ssid = ssid
        this.isRotating = isRotating
        this.ipAddress = ipAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.Rotating.name, isRotating)
                .put(SlaveInformation.MacAddress.name, macAddress)
                .put(SlaveInformation.IpAddress.name, ipAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateRotationItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ssid = body.getInt(SlaveInformation.Ssid.name)
        isRotating = body.getBoolean(SlaveInformation.Rotating.name)
        macAddress = body.getString(SlaveInformation.MacAddress.name)
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.UpdateRotation)
    }
}