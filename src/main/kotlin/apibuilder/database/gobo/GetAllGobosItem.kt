package apibuilder.database.gobo

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IConfiguration
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray

class GetAllGobosItem: IConfiguration, DatabaseResponse() {
    fun create(): GetAllGobosItem {
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): GetAllGobosItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetAllGobos,
                table = DatabaseType.Gobo
        )
    }
}