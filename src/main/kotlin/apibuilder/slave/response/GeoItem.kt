package apibuilder.slave.response

import apibuilder.slave.response.header.Header
import apibuilder.slave.response.header.SlaveResponse
import apibuilder.slave.response.interfaces.ISlaveResponseItem
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class GeoItem: ISlaveResponseItem, SlaveResponse() {
    lateinit var macAddress: String
    var positionX: Int = 0
    var positionY: Int = 0
    var positionZ: Int = 0
    var rotationX: Int = 0
    var rotationY: Int = 0
    var rotationZ: Int = 0
    var accelerationX: Int = 0
    var accelerationY: Int = 0
    var accelerationZ: Int = 0

    fun create(positionX: Int, positionY: Int, positionZ: Int, rotationX: Int, rotationY: Int, rotationZ: Int,
               accelerationX: Int, accelerationY: Int, accelerationZ: Int, macAddress: String): GeoItem {
        this.positionX = positionX
        this.positionY = positionY
        this.positionZ = positionZ
        this.rotationX = rotationX
        this.rotationY = rotationY
        this.rotationZ = rotationZ
        this.accelerationX = accelerationX
        this.accelerationY = accelerationY
        this.accelerationZ = accelerationZ
        this.macAddress = macAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.PositionX.name, positionX)
                .put(SlaveInformation.PositionY.name, positionY)
                .put(SlaveInformation.PositionZ.name, positionZ)
                .put(SlaveInformation.RotationX.name, rotationX)
                .put(SlaveInformation.RotationY.name, rotationY)
                .put(SlaveInformation.RotationZ.name, rotationZ)
                .put(SlaveInformation.AccelerationX.name, accelerationX)
                .put(SlaveInformation.AccelerationY.name, accelerationY)
                .put(SlaveInformation.AccelerationZ.name, accelerationZ)
                .put(SlaveInformation.MacAddress.name, macAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): GeoItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        positionX = body.getInt(SlaveInformation.PositionX.name)
        positionY = body.getInt(SlaveInformation.PositionY.name)
        positionZ = body.getInt(SlaveInformation.PositionZ.name)
        rotationX = body.getInt(SlaveInformation.RotationX.name)
        rotationY = body.getInt(SlaveInformation.RotationY.name)
        rotationZ = body.getInt(SlaveInformation.RotationZ.name)
        accelerationX = body.getInt(SlaveInformation.AccelerationX.name)
        accelerationY = body.getInt(SlaveInformation.AccelerationY.name)
        accelerationZ = body.getInt(SlaveInformation.AccelerationZ.name)
        macAddress = body.getString(SlaveInformation.MacAddress.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = enumstorage.slave.SlaveResponse.Geo)
    }
}