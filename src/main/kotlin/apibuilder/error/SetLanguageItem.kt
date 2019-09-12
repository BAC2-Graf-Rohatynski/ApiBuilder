package apibuilder.error

import apibuilder.error.header.ErrorResponse
import apibuilder.error.header.Header
import apibuilder.error.interfaces.IErrorItem
import enumstorage.language.Language
import enumstorage.message.Message
import enumstorage.message.MessageCommand
import org.json.JSONArray
import org.json.JSONObject

class SetLanguageItem: IErrorItem, ErrorResponse() {
    lateinit var language: String

    fun create(language: Language): SetLanguageItem {
        this.language = language.name
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = JSONObject().put(Message.Language.name, language)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): SetLanguageItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        language = body.getString(Message.Language.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = MessageCommand.SetLanguage)
    }
}