package apibuilder.database.configuration

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IConfiguration
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateConfigurationBySsidItem: IConfiguration, DatabaseResponse() {
    lateinit var ddfHash: String
    lateinit var ddfFile: String
    var ssid: Int = 0

    fun create(ddfFile: String, ssid: Int, ddfHash: String): UpdateConfigurationBySsidItem {
        this.ddfHash = ddfHash
        this.ddfFile = ddfFile
        this.ssid = ssid
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.DdfFile.name, ddfFile)
                .put(SlaveInformation.DdfHash.name, ddfHash)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateConfigurationBySsidItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ddfHash = body.getString(SlaveInformation.DdfHash.name)
        ddfFile = body.getString(SlaveInformation.DdfFile.name)
        ssid = body.getInt(SlaveInformation.Ssid.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateConfigurationBySsid,
                table = DatabaseType.Configuration
        )
    }
}