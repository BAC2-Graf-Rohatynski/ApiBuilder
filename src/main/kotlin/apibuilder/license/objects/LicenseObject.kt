package apibuilder.license.objects

import enumstorage.license.LicenseInformation
import enumstorage.license.LicenseState
import enumstorage.license.LicenseType
import enumstorage.port.SerialPorts
import org.json.JSONObject
import propertystorage.LicenseProperties
import java.lang.Exception

class LicenseObject {
    var id: Int = 0
    lateinit var type: String
    lateinit var createdAt: String
    lateinit var expiresAt: String
    lateinit var state: String
    lateinit var serialNumber: String
    lateinit var features: JSONObject
    var isDefault: Boolean = false

    fun create(id: Int, type: String, createdAt: String, expiresAt: String, state: String, serialNumber: String, isDefault: Boolean): LicenseObject {
        this.id = id
        this.type = type
        this.createdAt = createdAt
        this.expiresAt = expiresAt
        this.state = state
        this.serialNumber = serialNumber
        this.isDefault = isDefault
        setFeatures()
        return this
    }

    fun create(message: JSONObject): LicenseObject {
        this.id = message.getInt(LicenseInformation.Id.name)
        this.type = message.getString(LicenseInformation.Type.name)
        this.createdAt = message.getString(LicenseInformation.CreatedAt.name)
        this.expiresAt = message.getString(LicenseInformation.ExpiresAt.name)
        this.state = message.getString(LicenseInformation.State.name)
        this.serialNumber = message.getString(LicenseInformation.SerialNumber.name)
        setFeatures()
        return this
    }

    fun toJson(): JSONObject = JSONObject()
            .put(LicenseInformation.Id.name, id)
            .put(LicenseInformation.Type.name, type)
            .put(LicenseInformation.CreatedAt.name, createdAt)
            .put(LicenseInformation.ExpiresAt.name, expiresAt)
            .put(LicenseInformation.State.name, state)
            .put(LicenseInformation.SerialNumber.name, serialNumber)
            .put(LicenseInformation.IsDefault.name, isDefault)

    private fun setFeatures() {
        val features = when(type) {
            LicenseType.Basic.name -> LicenseProperties.getAdvanced()
            LicenseType.Default.name -> LicenseProperties.getAdvanced()
            LicenseType.Advanced.name -> LicenseProperties.getAdvanced()
            LicenseType.Extended.name -> LicenseProperties.getAdvanced()
            LicenseType.Full.name -> LicenseProperties.getAdvanced()
            else -> throw Exception("License type '$type' isn't supported!")
        }

        this.features = JSONObject()
                .put(SerialPorts.COM1.name, features[0])
                .put(SerialPorts.COM2.name, features[1])
                .put(SerialPorts.COM3.name, features[2])
                .put(SerialPorts.COM4.name, features[3])
                .put(SerialPorts.Artnet.name, features[4])
    }
}