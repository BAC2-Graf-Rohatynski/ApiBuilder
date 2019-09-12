package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.slave.SlaveCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateRotationSlaveItem: ISlaveRequestItem, SlaveRequest() {
    var isRotating: Boolean = false
    lateinit var ipAddress: String

    fun create(isRotating: Boolean, ipAddress: String): UpdateRotationSlaveItem {
        this.isRotating = isRotating
        this.ipAddress = ipAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Rotating.name, isRotating)
                .put(SlaveInformation.IpAddress.name, ipAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateRotationSlaveItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        isRotating = body.getBoolean(SlaveInformation.Rotating.name)
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.UpdateRotation)
    }
}