package apibuilder.license.response

import apibuilder.RsaModule
import apibuilder.license.objects.LicenseObject
import apibuilder.license.header.Header
import apibuilder.license.header.LicenseResponse
import apibuilder.license.interfaces.IResponse
import enumstorage.api.ApiValue
import org.json.JSONArray
import org.json.JSONObject

class ResponseItem: IResponse, LicenseResponse() {
    lateinit var headerObject: JSONObject
    lateinit var messageBody: JSONObject
    lateinit var licenses: List<LicenseObject>

    fun create(message: String, licenses: List<LicenseObject>? = null): ResponseItem {
        val apiMessage = RsaModule.decryptApiMessage(message = message)
        headerObject = apiMessage.first() as JSONObject
        messageBody = apiMessage.last() as JSONObject
        this.licenses = licenses ?: listOf()
        buildHeader()
        return this
    }

    fun getOriginalMessage(): JSONArray = JSONArray()
            .put(header)
            .put(messageBody)

    override fun toJson(): String {
        val body = JSONArray()

        licenses.forEach { license ->
            body.put(license.toJson())
        }

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): ResponseItem {
        header = parseToHeaderObject(message = message)

        val apiMessage = parseToBodyObject(message = message)
        messageBody = apiMessage[1] as JSONObject
        val body = apiMessage[2] as JSONArray

        val list = mutableListOf<LicenseObject>()
        body.forEach { license ->
            license as JSONObject
            list.add(LicenseObject().create(message = license))
        }

        licenses = list
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = headerObject.getString(ApiValue.Command.name))
    }
}