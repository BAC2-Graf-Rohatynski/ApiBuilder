package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.api.ApiValue
import enumstorage.network.NetworkCommand
import org.json.JSONArray
import org.json.JSONObject

class EnableShowItem: INetworkItem, NetworkResponse() {
    lateinit var show: String

    fun create(show: String): EnableShowItem {
        this.show = show
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject().put(ApiValue.Show.name, show)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): EnableShowItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        show = body.getString(ApiValue.Show.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.EnableShow)
    }
}