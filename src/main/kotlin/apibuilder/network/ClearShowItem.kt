package apibuilder.network

import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.network.NetworkCommand
import org.json.JSONArray

class ClearShowItem: INetworkItem, NetworkResponse() {
    fun create(): ClearShowItem {
        buildHeader()
        return this
    }

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): ClearShowItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.ClearShow)
    }
}