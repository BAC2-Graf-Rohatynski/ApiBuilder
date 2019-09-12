package apibuilder.error

import apibuilder.error.header.ErrorResponse
import apibuilder.error.interfaces.IErrorItem
import enumstorage.message.MessageCommand
import org.json.JSONArray

class GetAllMessagesItem: IErrorItem, ErrorResponse() {
    fun create(): GetAllMessagesItem {
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): GetAllMessagesItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = apibuilder.error.header.Header().build(command = MessageCommand.GetAllMessages)
    }
}