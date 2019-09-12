package apibuilder.license

import apibuilder.license.header.Header
import apibuilder.license.header.LicenseResponse
import apibuilder.license.interfaces.ILicenseItem
import enumstorage.license.LicenseDatabaseCommand
import org.json.JSONArray

class GetAllLicensesItem: ILicenseItem, LicenseResponse() {
    fun create(): GetAllLicensesItem {
        buildHeader()
        return this
    }

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): GetAllLicensesItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = LicenseDatabaseCommand.GetAllLicenses.name)
    }
}