package apibuilder.license

import apibuilder.license.header.Header
import apibuilder.license.header.LicenseResponse
import apibuilder.license.interfaces.ILicenseItem
import enumstorage.license.LicenseDatabaseCommand
import enumstorage.license.LicenseInformation
import org.json.JSONArray
import org.json.JSONObject

class AddLicenseItem: ILicenseItem, LicenseResponse() {
    var id: Int = 0
    lateinit var expiresAt: String
    lateinit var createdAt: String
    lateinit var type: String
    lateinit var serialNumber: String
    lateinit var state: String
    val isDefault = false

    fun create(id: Int, expiresAt: String, createdAt: String, type: String, serialNumber: String, state: String): AddLicenseItem {
        this.id = id
        this.expiresAt = expiresAt
        this.createdAt = createdAt
        this.type = type
        this.serialNumber = serialNumber
        this.state = state
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(LicenseInformation.Id.name, id)
                .put(LicenseInformation.ExpiresAt.name, expiresAt)
                .put(LicenseInformation.CreatedAt.name, createdAt)
                .put(LicenseInformation.Type.name, type)
                .put(LicenseInformation.SerialNumber.name, serialNumber)
                .put(LicenseInformation.IsDefault.name, isDefault)
                .put(LicenseInformation.State.name, state)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): AddLicenseItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        id = body.getInt(LicenseInformation.Id.name)
        expiresAt = body.getString(LicenseInformation.ExpiresAt.name)
        createdAt = body.getString(LicenseInformation.CreatedAt.name)
        type = body.getString(LicenseInformation.Type.name)
        serialNumber = body.getString(LicenseInformation.SerialNumber.name)
        state = body.getString(LicenseInformation.State.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = LicenseDatabaseCommand.AddLicense.name)
    }
}