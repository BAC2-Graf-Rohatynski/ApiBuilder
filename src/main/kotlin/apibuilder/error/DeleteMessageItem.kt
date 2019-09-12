package apibuilder.error

import apibuilder.error.header.ErrorResponse
import apibuilder.error.interfaces.IErrorItem
import enumstorage.message.Message
import enumstorage.message.MessageCommand
import org.json.JSONArray
import org.json.JSONObject

class DeleteMessageItem: IErrorItem, ErrorResponse() {
    var code: Int = 0

    fun create(code: Int): DeleteMessageItem {
        this.code = code
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject().put(Message.Code.name, code)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): DeleteMessageItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        code = body.getInt(Message.Code.name)
        return this
    }

    override fun buildHeader() {
        this.header = apibuilder.error.header.Header().build(command = MessageCommand.DeleteMessage)
    }
}