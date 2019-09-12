package apibuilder.port

import apibuilder.port.header.Header
import apibuilder.port.header.PortResponse
import apibuilder.port.interfaces.IPortItem
import enumstorage.license.LicenseDatabaseCommand
import org.json.JSONArray

class ActiveLicenseItem: IPortItem, PortResponse() {
    fun create(): ActiveLicenseItem {
        buildHeader()
        return this
    }

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): ActiveLicenseItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = LicenseDatabaseCommand.GetActiveLicense.name)
    }
}