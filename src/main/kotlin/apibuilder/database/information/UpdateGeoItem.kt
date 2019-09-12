package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateGeoItem: IInformation, DatabaseResponse() {
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

    fun create(macAddress: String, positionX: Int, positionY: Int, positionZ: Int, rotationX: Int, rotationY: Int,
              rotationZ: Int, accelerationX: Int, accelerationY: Int, accelerationZ: Int): UpdateGeoItem {
        this.macAddress = macAddress
        this.positionX = positionX
        this.positionY = positionY
        this.positionZ = positionZ
        this.rotationX = rotationX
        this.rotationY = rotationY
        this.rotationZ = rotationZ
        this.accelerationX = accelerationX
        this.accelerationY = accelerationY
        this.accelerationZ = accelerationZ
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.MacAddress.name, macAddress)
                .put(SlaveInformation.PositionX.name, positionX)
                .put(SlaveInformation.PositionY.name, positionY)
                .put(SlaveInformation.PositionZ.name, positionZ)
                .put(SlaveInformation.RotationX.name, rotationX)
                .put(SlaveInformation.RotationY.name, rotationY)
                .put(SlaveInformation.RotationZ.name, rotationZ)
                .put(SlaveInformation.AccelerationX.name, accelerationX)
                .put(SlaveInformation.AccelerationY.name, accelerationY)
                .put(SlaveInformation.AccelerationZ.name, accelerationZ)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateGeoItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        macAddress = body.getString(SlaveInformation.MacAddress.name)
        positionX = body.getInt(SlaveInformation.PositionX.name)
        positionY = body.getInt(SlaveInformation.PositionY.name)
        rotationX = body.getInt(SlaveInformation.PositionZ.name)
        positionZ = body.getInt(SlaveInformation.RotationX.name)
        rotationY = body.getInt(SlaveInformation.RotationY.name)
        rotationZ = body.getInt(SlaveInformation.RotationZ.name)
        accelerationX = body.getInt(SlaveInformation.AccelerationX.name)
        accelerationY = body.getInt(SlaveInformation.AccelerationY.name)
        accelerationZ = body.getInt(SlaveInformation.AccelerationZ.name)
        return this
    }


    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateGeo,
                table = DatabaseType.Information
        )
    }
}