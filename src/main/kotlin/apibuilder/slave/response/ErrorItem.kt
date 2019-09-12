package apibuilder.slave.response

import apibuilder.slave.response.header.Header
import apibuilder.slave.response.header.SlaveResponse
import apibuilder.slave.response.interfaces.ISlaveResponseItem
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class ErrorItem: ISlaveResponseItem, SlaveResponse() {
    var ssid: Int = 0
    var errorCode: Int = 0

    fun create(ssid: Int, errorCode: Int): ErrorItem {
        this.ssid = ssid
        this.errorCode = errorCode
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.Ssid.name, ssid)
                .put(SlaveInformation.ErrorCode.name, errorCode)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): ErrorItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        ssid = body.getInt(SlaveInformation.Ssid.name)
        errorCode = body.getInt(SlaveInformation.ErrorCode.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = enumstorage.slave.SlaveResponse.Error)
    }
}