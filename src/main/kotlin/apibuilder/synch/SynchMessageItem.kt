package apibuilder.synch

import apibuilder.synch.header.Header
import apibuilder.synch.interfaces.ISynchItem
import apibuilder.synch.header.SynchResponse
import enumstorage.master.MasterInformation
import enumstorage.master.MasterMessage
import enumstorage.slave.SlaveInformation
import enumstorage.synch.SynchRequest
import org.json.JSONArray
import org.json.JSONObject

class SynchMessageItem: ISynchItem, SynchResponse() {
    lateinit var ipAddress: String
    lateinit var databaseMessage: JSONArray
    var timestamp: Long = System.currentTimeMillis()

    fun create(ipAddress: String, message: JSONArray): SynchMessageItem {
        this.databaseMessage = message
        this.ipAddress = ipAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(MasterInformation.IpAddress.name, ipAddress)
                .put(MasterMessage.Message.name, databaseMessage)
                .put(MasterMessage.Timestamp.name, timestamp)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): SynchMessageItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(MasterInformation.IpAddress.name)
        databaseMessage = JSONArray(body.getString(SlaveInformation.IpAddress.name))
        timestamp = body.getLong(MasterInformation.Port.name)
        buildHeader()

        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = SynchRequest.SendDatabaseMessage.name,
                ipAddress = ipAddress)
    }
}