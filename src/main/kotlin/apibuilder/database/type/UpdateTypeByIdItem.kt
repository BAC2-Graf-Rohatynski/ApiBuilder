package apibuilder.database.type

import apibuilder.database.type.item.TypeItem
import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IType
import enumstorage.type.Type
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray
import org.json.JSONObject

class UpdateTypeByIdItem: IType, DatabaseResponse() {
    lateinit var item: TypeItem

    fun create(item: TypeItem): UpdateTypeByIdItem {
        this.item = item
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = item.toJson()

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateTypeByIdItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        item = TypeItem().build(
                id = body.getInt(Type.Id.name),
                typeDe = body.getString(Type.TypeDe.name),
                typeEn = body.getString(Type.TypeEn.name),
                isStandard = body.getBoolean(Type.IsStandard.name)
        )
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateTypeById,
                table = DatabaseType.Type
        )
    }
}