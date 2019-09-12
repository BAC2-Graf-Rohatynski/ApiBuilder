package apibuilder.error

import apibuilder.error.header.ErrorResponse
import apibuilder.error.interfaces.IErrorItem
import enumstorage.message.Message
import enumstorage.message.MessageCommand
import org.json.JSONArray
import org.json.JSONObject

class SendMessageItem: IErrorItem, ErrorResponse() {
    var code: Int = 0
    var timestamp: Long = 0
    var enabled: Boolean = false
    var ssid: Int = 0

    fun create(code: Int, timestamp: Long, enabled: Boolean, ssid: Int = 0): SendMessageItem {
        this.code = code
        this.timestamp = timestamp
        this.enabled = enabled
        this.ssid = ssid
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject()
                .put(Message.Code.name, code)
                .put(Message.Timestamp.name, timestamp)
                .put(Message.Enabled.name, enabled)
                .put(Message.Ssid.name, ssid)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): SendMessageItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        code = body.getInt(Message.Code.name)
        timestamp = body.getLong(Message.Timestamp.name)
        enabled = body.getBoolean(Message.Enabled.name)
        ssid = body.getInt(Message.Ssid.name)
        return this
    }

    override fun buildHeader() {
        this.header = apibuilder.error.header.Header().build(command = MessageCommand.SendMessage)
    }
}