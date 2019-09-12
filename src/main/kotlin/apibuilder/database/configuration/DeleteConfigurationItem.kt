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

class DeleteConfigurationItem: IConfiguration, DatabaseResponse() {
    lateinit var show: String
    var ssid: Int = 0

    fun create(show: String, ssid: Int): DeleteConfigurationItem {
        this.show = show
        this.ssid = ssid
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(ApiValue.Show.name, show)
                .put(SlaveInformation.Ssid.name, ssid)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): DeleteConfigurationItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        show = body.getString(ApiValue.Show.name)
        ssid = body.getInt(SlaveInformation.Ssid.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.DeleteConfiguration,
                table = DatabaseType.Configuration
        )
    }
}