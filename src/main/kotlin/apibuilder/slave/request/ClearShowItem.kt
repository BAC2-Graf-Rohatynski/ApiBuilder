package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.slave.SlaveCommand
import org.json.JSONArray

class ClearShowItem: ISlaveRequestItem, SlaveRequest() {
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
        this.header = Header().build(command = SlaveCommand.ClearShow)
    }
}