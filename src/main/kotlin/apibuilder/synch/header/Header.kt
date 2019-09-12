package apibuilder.synch.header

import enumstorage.api.ApiValue
import enumstorage.master.MasterInformation
import org.json.JSONObject
import propertystorage.MasterProperties
import propertystorage.PortProperties

class Header: IHeader {
    lateinit var command: String
    lateinit var ipAddress: String
    var identification: String? = null
    var port: Int? = null

    fun build(command: String, ipAddress: String, identification: String? = null, port: Int? = null): Header {
        this.command = command
        this.ipAddress = ipAddress
        this.identification = identification
        this.port = port
        return this
    }

    override fun toJson(): JSONObject {
        if (!::command.isInitialized) {
            throw Exception("Object not initialized!")
        }

        return JSONObject()
                .put(ApiValue.Command.name, command)
                .put(MasterInformation.IpAddress.name, ipAddress)
                .put(MasterInformation.Identification.name, identification ?: MasterProperties.getIdentification())
                .put(MasterInformation.Port.name, port ?: PortProperties.getUdpPort())
    }
}