package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.slave.SlaveCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateSsidSlaveItem: ISlaveRequestItem, SlaveRequest() {
    var ssid: Int = 0
    lateinit var ipAddress: String

    fun create(ssid: Int, ipAddress: String): UpdateSsidSlaveItem {
        this.ssid = ssid
        this.ipAddress = ipAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.IpAddress.name, ipAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateSsidSlaveItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ssid = body.getInt(SlaveInformation.Ssid.name)
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.UpdateSsid)
    }
}