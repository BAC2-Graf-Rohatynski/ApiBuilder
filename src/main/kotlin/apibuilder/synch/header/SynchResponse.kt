package apibuilder.synch.header

import apibuilder.RsaModule
import apibuilder.synch.interfaces.ISynchItem
import enumstorage.api.ApiValue
import enumstorage.master.MasterInformation
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

abstract class SynchResponse {
    lateinit var header: Header

    fun parseToHeaderObject(message: String): Header {
        val apiMessage = RsaModule.decryptApiMessage(message = message)
        val header = apiMessage.first() as JSONObject

        return Header().build(
                command = header.getString(ApiValue.Command.name),
                ipAddress = header.getString(MasterInformation.IpAddress.name),
                identification = header.getString(MasterInformation.Identification.name),
                port = header.getInt(MasterInformation.Port.name))
    }

    fun parseToBodyObject(message: String): JSONArray =  RsaModule.decryptApiMessage(message = message)

    fun encryptApiMessage(message: JSONArray) = RsaModule.encryptApiMessage(message = message)
}