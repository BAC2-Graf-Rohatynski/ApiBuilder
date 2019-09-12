package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class GetSlaveByTypeItem: IInformation, DatabaseResponse() {
    lateinit var type: String

    fun create(type: String): GetSlaveByTypeItem {
        this.type = type
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject().put(SlaveInformation.Type.name, type)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GetSlaveByTypeItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        type = body.getString(SlaveInformation.Type.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetSlaveByType,
                table = DatabaseType.Information
        )
    }
}