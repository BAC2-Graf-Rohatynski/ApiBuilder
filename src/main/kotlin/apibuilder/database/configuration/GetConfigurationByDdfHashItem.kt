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

class GetConfigurationByDdfHashItem: IConfiguration, DatabaseResponse() {
    lateinit var show: String
    lateinit var ddfHash: String

    fun create(show: String, ddfHash: String): GetConfigurationByDdfHashItem {
        this.show = show
        this.ddfHash = ddfHash
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(ApiValue.Show.name, show)
                .put(SlaveInformation.DdfHash.name, ddfHash)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GetConfigurationByDdfHashItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        show = body.getString(ApiValue.Show.name)
        ddfHash = body.getString(SlaveInformation.DdfHash.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetConfigurationByDdfHash,
                table = DatabaseType.Configuration
        )
    }

}