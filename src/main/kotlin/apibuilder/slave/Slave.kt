package apibuilder.slave

import apibuilder.RsaModule
import enumstorage.api.ApiValue
import enumstorage.slave.SlaveInformation
import enumstorage.slave.SlaveResponse
import org.json.JSONObject

class Slave: ISlave {
    lateinit var responseType: SlaveResponse
    var ssid = -1
    var ddfFile = String()
    var show = String()
    var macAddress = String()
    var macAddressTemporary = String()
    var ipAddress = String()
    var ddfHash = String()
    var deviceImageHash = String()
    var goboImageHash = String()
    var status = String()
    var type = String()
    var device = String()
    var manufacturer = String()
    var isRotating = false
    var positionX = 0
    var positionY = 0
    var positionZ = 0
    var rotationX = 0
    var rotationY = 0
    var rotationZ = 0
    var accelerationX = 0
    var accelerationY = 0
    var accelerationZ = 0
    var errorCode = mutableListOf<Int>()
    var commandPort = 0
    var timestamp: Long = 0

    @Synchronized
    override fun build(message: String): Slave {
        val apiMessage = RsaModule.decryptApiMessage(message = message).last() as JSONObject
        return build(message = apiMessage)
    }

    @Synchronized
    override fun build(message: JSONObject): Slave {
        buildInformation(information = message)
        buildGeo(information = message)
        buildConfiguration(configuration = message)
        return this
    }

    @Synchronized
    override fun toJson(): JSONObject {
        return JSONObject()
                .put(ApiValue.Show.name, show)
                .put(SlaveInformation.DdfFile.name, ddfFile)
                .put(SlaveInformation.DdfHash.name, ddfHash)
                .put(SlaveInformation.MacAddress.name, macAddress)
                .put(SlaveInformation.MacAddressTemporary.name, macAddressTemporary)
                .put(SlaveInformation.IpAddress.name, ipAddress)
                .put(SlaveInformation.DeviceImageHash.name, deviceImageHash)
                .put(SlaveInformation.GoboImageHash.name, goboImageHash)
                .put(SlaveInformation.Status.name, status)
                .put(SlaveInformation.Type.name, type)
                .put(SlaveInformation.Device.name, device)
                .put(SlaveInformation.Manufacturer.name, manufacturer)
                .put(SlaveInformation.Rotating.name, isRotating)
                .put(SlaveInformation.PositionX.name, positionX)
                .put(SlaveInformation.PositionY.name, positionY)
                .put(SlaveInformation.PositionZ.name, positionZ)
                .put(SlaveInformation.RotationX.name, rotationX)
                .put(SlaveInformation.RotationY.name, rotationY)
                .put(SlaveInformation.RotationZ.name, rotationZ)
                .put(SlaveInformation.AccelerationX.name, accelerationX)
                .put(SlaveInformation.AccelerationY.name, accelerationY)
                .put(SlaveInformation.AccelerationZ.name, accelerationZ)
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.Timestamp.name, timestamp)
    }

    @Synchronized
    override fun parseGreeting(message: String): Slave {
        val apiMessage = RsaModule.decryptApiMessage(message = message)

        responseType = SlaveResponse.Greeting
        val information = apiMessage.first() as JSONObject
        commandPort = information.getInt(SlaveInformation.CommandPort.name)
        ipAddress = information.getString(SlaveInformation.IpAddress.name)
        macAddress = information.getString(SlaveInformation.MacAddress.name)
        macAddressTemporary = macAddress
        return this
    }

    @Synchronized
    override fun parseGeo(message: String): Slave {
        val apiMessage = RsaModule.decryptApiMessage(message = message)

        responseType = SlaveResponse.Geo
        buildGeo(information = apiMessage.first() as JSONObject)
        return this
    }

    @Synchronized
    override fun parseError(message: String): Slave {
        val apiMessage = RsaModule.decryptApiMessage(message = message)

        responseType = SlaveResponse.Error
        val information = apiMessage.first() as JSONObject
        ssid = information.getInt(SlaveInformation.Ssid.name)
        errorCode.add(information.getInt(SlaveInformation.ErrorCode.name))
        return this
    }

    @Synchronized
    override fun parseConnect(message: String): Slave {
        val apiMessage = RsaModule.decryptApiMessage(message = message)

        responseType = SlaveResponse.Connect
        buildInformation(information = apiMessage.first() as JSONObject)
        buildConfiguration(configuration = apiMessage.last() as JSONObject)
        return this
    }

    @Synchronized
    override fun parseFirstConnect(message: String): Slave {
        val apiMessage = RsaModule.decryptApiMessage(message = message)

        responseType = SlaveResponse.FirstConnect
        buildInformation(information = apiMessage.first() as JSONObject)
        return this
    }

    @Synchronized
    override fun parseRead(message: String): Slave {
        val apiMessage = RsaModule.decryptApiMessage(message = message)

        responseType = SlaveResponse.Read
        buildInformation(information = apiMessage.first() as JSONObject)
        buildGeo(information = apiMessage.first() as JSONObject)
        buildConfiguration(configuration = apiMessage.last() as JSONObject)
        return this
    }

    private fun buildInformation(information: JSONObject) {
        ssid = if (information.has(SlaveInformation.Ssid.name)) information.getInt(SlaveInformation.Ssid.name) else 0
        show = if (information.has(ApiValue.Show.name)) information.getString(ApiValue.Show.name) else String()
        macAddress = if (information.has(SlaveInformation.MacAddress.name)) information.getString(SlaveInformation.MacAddress.name) else String()
        macAddressTemporary = macAddress
        ipAddress = if (information.has(SlaveInformation.IpAddress.name)) information.getString(SlaveInformation.IpAddress.name) else String()
        deviceImageHash = if (information.has(SlaveInformation.DeviceImageHash.name)) information.getString(SlaveInformation.DeviceImageHash.name) else String()
        goboImageHash = if (information.has(SlaveInformation.GoboImageHash.name)) information.getString(SlaveInformation.GoboImageHash.name) else String()
        status = if (information.has(SlaveInformation.Status.name)) information.getString(SlaveInformation.Status.name) else String()
        type = if (information.has(SlaveInformation.Type.name)) information.getString(SlaveInformation.Type.name) else String()
        device = if (information.has(SlaveInformation.Device.name)) information.getString(SlaveInformation.Device.name) else String()
        manufacturer = if (information.has(SlaveInformation.Manufacturer.name)) information.getString(SlaveInformation.Manufacturer.name) else String()
        isRotating = if (information.has(SlaveInformation.Rotating.name)) information.getBoolean(SlaveInformation.Rotating.name) else false
        timestamp = if (information.has(SlaveInformation.Timestamp.name)) information.getLong(SlaveInformation.Timestamp.name) else 0
    }

    private fun buildGeo(information: JSONObject) {
        positionX = if (information.has(SlaveInformation.PositionX.name)) information.getInt(SlaveInformation.PositionX.name) else 0
        positionY = if (information.has(SlaveInformation.PositionY.name)) information.getInt(SlaveInformation.PositionY.name) else 0
        positionZ = if (information.has(SlaveInformation.PositionZ.name)) information.getInt(SlaveInformation.PositionZ.name) else 0
        rotationX = if (information.has(SlaveInformation.RotationX.name)) information.getInt(SlaveInformation.RotationX.name) else 0
        rotationY = if (information.has(SlaveInformation.RotationY.name)) information.getInt(SlaveInformation.RotationY.name) else 0
        rotationZ = if (information.has(SlaveInformation.RotationZ.name)) information.getInt(SlaveInformation.RotationZ.name) else 0
        accelerationX = if (information.has(SlaveInformation.AccelerationX.name)) information.getInt(SlaveInformation.AccelerationX.name) else 0
        accelerationY = if (information.has(SlaveInformation.AccelerationY.name)) information.getInt(SlaveInformation.AccelerationY.name) else 0
        accelerationZ = if (information.has(SlaveInformation.AccelerationZ.name)) information.getInt(SlaveInformation.AccelerationZ.name) else 0
    }

    private fun buildConfiguration(configuration: JSONObject) {
        ddfHash = if (configuration.has(SlaveInformation.DdfHash.name)) configuration.getString(SlaveInformation.DdfHash.name) else String()
        ddfFile = if (configuration.has(SlaveInformation.DdfFile.name)) configuration.getString(SlaveInformation.DdfFile.name) else String()
    }
}