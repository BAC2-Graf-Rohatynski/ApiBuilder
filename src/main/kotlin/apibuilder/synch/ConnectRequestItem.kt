package apibuilder.synch

import apibuilder.synch.header.Header
import apibuilder.synch.interfaces.ISynchItem
import apibuilder.synch.header.SynchResponse
import enumstorage.master.MasterInformation
import enumstorage.synch.SynchRequest
import org.json.JSONArray
import org.json.JSONObject

class ConnectRequestItem: ISynchItem, SynchResponse() {
    lateinit var ipAddress: String
    lateinit var identification: String
    var port: Int = 0

    fun create(ipAddress: String, identification: String, port: Int): ConnectRequestItem {
        this.ipAddress = ipAddress
        this.identification = identification
        this.port = port
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(MasterInformation.IpAddress.name, ipAddress)
                .put(MasterInformation.Port.name, port)
                .put(MasterInformation.Identification.name, identification)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): ConnectRequestItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(MasterInformation.IpAddress.name)
        port = body.getInt(MasterInformation.Port.name)
        identification = body.getString(MasterInformation.Identification.name)
        buildHeader()

        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = SynchRequest.ConnectRequest.name,
                ipAddress = ipAddress)
    }
}