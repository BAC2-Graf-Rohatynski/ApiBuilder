package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.api.ApiValue
import enumstorage.database.DatabaseValue
import enumstorage.network.NetworkCommand
import org.json.JSONArray
import org.json.JSONObject

class RenameShowItem: INetworkItem, NetworkResponse() {
    lateinit var show: String
    lateinit var newShow: String

    fun create(show: String, newShow: String): RenameShowItem {
        this.show = show
        this.newShow = newShow
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(ApiValue.Show.name, show)
                .put(DatabaseValue.Value.name, newShow)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): RenameShowItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        show = body.getString(ApiValue.Show.name)
        newShow = body.getString(DatabaseValue.Value.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.RenameShow)
    }
}