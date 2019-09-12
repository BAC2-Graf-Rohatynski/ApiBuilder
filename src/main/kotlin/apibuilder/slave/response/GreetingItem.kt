package apibuilder.slave.response

import apibuilder.slave.response.header.Header
import apibuilder.slave.response.header.SlaveResponse
import apibuilder.slave.response.interfaces.ISlaveResponseItem
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class GreetingItem: ISlaveResponseItem, SlaveResponse() {
    lateinit var ipAddress: String
    lateinit var macAddress: String
    var commandPort: Int = 0

    fun create(ipAddress: String, macAddress: String, commandPort: Int): GreetingItem {
        this.ipAddress = ipAddress
        this.macAddress = macAddress
        this.commandPort = commandPort
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.IpAddress.name, ipAddress)
                .put(SlaveInformation.MacAddress.name, macAddress)
                .put(SlaveInformation.CommandPort.name, commandPort)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GreetingItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        macAddress = body.getString(SlaveInformation.MacAddress.name)
        commandPort = body.getInt(SlaveInformation.CommandPort.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = enumstorage.slave.SlaveResponse.Greeting)
    }
}