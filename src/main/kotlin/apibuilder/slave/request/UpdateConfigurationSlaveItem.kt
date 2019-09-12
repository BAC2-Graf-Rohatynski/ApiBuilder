package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.slave.SlaveCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateConfigurationSlaveItem: ISlaveRequestItem, SlaveRequest() {
    lateinit var ddfFile: String
    lateinit var ddfHash: String
    lateinit var ipAddress: String

    fun create(ddfFile: String, ddfHash: String, ipAddress: String): UpdateConfigurationSlaveItem {
        this.ddfFile = ddfFile
        this.ddfHash = ddfHash
        this.ipAddress = ipAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.DdfFile.name, ddfFile)
                .put(SlaveInformation.DdfHash.name, ddfHash)
                .put(SlaveInformation.IpAddress.name, ipAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateConfigurationSlaveItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        ddfFile = body.getString(SlaveInformation.DdfFile.name)
        ddfHash = body.getString(SlaveInformation.DdfHash.name)
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.UpdateConfiguration)
    }
}