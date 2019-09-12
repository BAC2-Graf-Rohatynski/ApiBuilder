package apibuilder.database.configuration

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IConfiguration
import enumstorage.api.ApiValue
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class AddConfigurationItem: IConfiguration, DatabaseResponse() {
    lateinit var ddfHash: String
    lateinit var ddfFile: String
    lateinit var show: String
    var ssid: Int = 0

    fun create(ddfHash: String, ddfFile: String, ssid: Int, show: String): AddConfigurationItem {
        this.ddfHash = ddfHash
        this.ddfFile = ddfFile
        this.show = show
        this.ssid = ssid
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.DdfHash.name, ddfHash)
                .put(SlaveInformation.DdfFile.name, ddfFile)
                .put(SlaveInformation.Ssid.name, ssid)
                .put(ApiValue.Show.name, show)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): AddConfigurationItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ddfHash = body.getString(SlaveInformation.DdfHash.name)
        ddfFile = body.getString(SlaveInformation.DdfFile.name)
        ssid = body.getInt(SlaveInformation.Ssid.name)
        show = body.getString(ApiValue.Show.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.AddConfiguration,
                table = DatabaseType.Configuration
        )
    }
}