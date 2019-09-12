package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.database.DatabaseValue
import enumstorage.network.NetworkCommand
import org.json.JSONArray
import org.json.JSONObject

class ControlSsidHandlingItem: INetworkItem, NetworkResponse() {
    var isEnabled: Boolean = false

    fun create(isEnabled: Boolean): ControlSsidHandlingItem {
        this.isEnabled = isEnabled
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject().put(DatabaseValue.Value.name, isEnabled)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): ControlSsidHandlingItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        isEnabled = body.getBoolean(DatabaseValue.Value.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.ControlSsidHandling)
    }
}