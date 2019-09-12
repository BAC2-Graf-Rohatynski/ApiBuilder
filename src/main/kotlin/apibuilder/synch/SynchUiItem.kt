package apibuilder.synch

import apibuilder.synch.header.Header
import apibuilder.synch.interfaces.ISynchItem
import apibuilder.synch.header.SynchResponse
import enumstorage.api.ApiValue
import enumstorage.master.MasterInformation
import enumstorage.synch.SynchRequest
import org.json.JSONArray
import org.json.JSONObject

class SynchUiItem: ISynchItem, SynchResponse() {
    lateinit var ipAddress: String
    lateinit var identification: String
    lateinit var command: String

    fun create(ipAddress: String, identification: String, command: String): SynchUiItem {
        this.identification = identification
        this.ipAddress = ipAddress
        this.command = command
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(MasterInformation.IpAddress.name, ipAddress)
                .put(MasterInformation.Identification.name, identification)
                .put(ApiValue.Command.name, command)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): SynchUiItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(MasterInformation.IpAddress.name)
        identification = body.getString(MasterInformation.Identification.name)
        command = body.getString(ApiValue.Command.name)
        buildHeader()

        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = SynchRequest.SendDatabaseMessage.name,
                ipAddress = ipAddress)
    }
}