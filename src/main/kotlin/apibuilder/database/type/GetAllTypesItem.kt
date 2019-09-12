package apibuilder.database.type

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IType
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray

class GetAllTypesItem: IType, DatabaseResponse() {
    fun create(): GetAllTypesItem {
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): GetAllTypesItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.GetAllTypes,
                table = DatabaseType.Type
        )
    }
}