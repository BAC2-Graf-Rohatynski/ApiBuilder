package apibuilder.database.manufacturer

import apibuilder.database.manufacturer.item.ManufacturerItem
import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IManufacturer
import enumstorage.manufacturer.Manufacturer
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray
import org.json.JSONObject

class DeleteManufacturerItem: IManufacturer, DatabaseResponse() {
    lateinit var item: ManufacturerItem

    fun create(item: ManufacturerItem): DeleteManufacturerItem {
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

    override fun toObject(message: String): DeleteManufacturerItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        item = ManufacturerItem().build(
                id = body.getInt(Manufacturer.Id.name),
                manufacturerDe = body.getString(Manufacturer.ManufacturerDe.name),
                manufacturerEn = body.getString(Manufacturer.ManufacturerEn.name),
                isStandard = body.getBoolean(Manufacturer.IsStandard.name)
        )
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.DeleteManufacturer,
                table = DatabaseType.Manufacturer
        )
    }
}