package apibuilder.database.configuration

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IConfiguration
import enumstorage.api.ApiValue
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray
import org.json.JSONObject

class GetAllConfigurationsItem: IConfiguration, DatabaseResponse() {
    lateinit var show: String

    fun create(show: String): GetAllConfigurationsItem {
        this.show = show
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject().put(ApiValue.Show.name, show)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GetAllConfigurationsItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        show = body.getString(ApiValue.Show.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetAllConfigurations,
                table = DatabaseType.Configuration
        )
    }
}