package apibuilder.license

import apibuilder.license.header.Header
import apibuilder.license.header.LicenseResponse
import apibuilder.license.interfaces.ILicenseItem
import enumstorage.license.LicenseDatabaseCommand
import enumstorage.license.LicenseInformation
import org.json.JSONArray
import org.json.JSONObject

class ExtendExpirationDateItem: ILicenseItem, LicenseResponse() {
    var id: Int = 0
    lateinit var expiresAt: String

    fun create(id: Int, expiresAt: String): ExtendExpirationDateItem {
        this.id = id
        this.expiresAt = expiresAt
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(LicenseInformation.Id.name, id)
                .put(LicenseInformation.ExpiresAt.name, expiresAt)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): ExtendExpirationDateItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        id = body.getInt(LicenseInformation.Id.name)
        expiresAt = body.getString(LicenseInformation.ExpiresAt.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = LicenseDatabaseCommand.ExtendExpirationDate.name)
    }
}