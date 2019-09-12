package apibuilder.slave.response

import apibuilder.slave.response.header.Header
import apibuilder.slave.response.header.SlaveResponse
import apibuilder.slave.response.interfaces.ISlaveResponseItem
import org.json.JSONArray

class ImSlaveItem: ISlaveResponseItem, SlaveResponse() {
    fun create(): ImSlaveItem {
        buildHeader()
        return this
    }

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): ImSlaveItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = enumstorage.slave.SlaveResponse.ImSlave)
    }
}