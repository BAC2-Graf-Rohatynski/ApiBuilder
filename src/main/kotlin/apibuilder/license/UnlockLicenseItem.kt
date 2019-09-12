package apibuilder.license

import apibuilder.license.header.Header
import apibuilder.license.header.LicenseResponse
import apibuilder.license.interfaces.ILicenseItem
import enumstorage.license.LicenseDatabaseCommand
import enumstorage.license.LicenseInformation
import org.json.JSONArray
import org.json.JSONObject

class UnlockLicenseItem: ILicenseItem, LicenseResponse() {
    var id: Int = 0

    fun create(id: Int): UnlockLicenseItem {
        this.id = id
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject().put(LicenseInformation.Id.name, id)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UnlockLicenseItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        id = body.getInt(LicenseInformation.Id.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = LicenseDatabaseCommand.Unlock.name)
    }
}