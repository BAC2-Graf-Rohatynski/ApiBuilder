package apibuilder.database.information

import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IInformation
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateRotatingItem: IInformation, DatabaseResponse() {
    var isRotating: Boolean = false
    var ssid: Int = 0

    fun create(ssid: Int, isRotating: Boolean): UpdateRotatingItem {
        this.isRotating = isRotating
        this.ssid = ssid
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.Rotating.name, isRotating)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateRotatingItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ssid = body.getInt(SlaveInformation.Ssid.name)
        isRotating = body.getBoolean(SlaveInformation.Rotating.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.UpdateRotation,
                table = DatabaseType.Information
        )
    }
}