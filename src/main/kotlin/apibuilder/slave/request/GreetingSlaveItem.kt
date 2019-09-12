package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.master.MasterInformation
import enumstorage.slave.SlaveCommand
import org.json.JSONArray
import org.json.JSONObject
import propertystorage.MasterProperties
import propertystorage.PortProperties

class GreetingSlaveItem: ISlaveRequestItem, SlaveRequest() {
    lateinit var ipAddress: String
    var identification: String = MasterProperties.getIdentification()
    var port: Int = PortProperties.getUdpMasterPort()

    fun create(ipAddress: String): GreetingSlaveItem {
        this.ipAddress = ipAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(MasterInformation.Identification.name, identification)
                .put(MasterInformation.IpAddress.name, ipAddress)
                .put(MasterInformation.Port.name, port)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GreetingSlaveItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(MasterInformation.IpAddress.name)
        identification = body.getString(MasterInformation.Identification.name)
        port = body.getInt(MasterInformation.Port.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.Greeting)
    }
}