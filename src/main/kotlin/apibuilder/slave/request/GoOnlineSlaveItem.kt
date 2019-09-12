package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.slave.SlaveCommand
import org.json.JSONArray

class GoOnlineSlaveItem: ISlaveRequestItem, SlaveRequest() {
    fun create(): GoOnlineSlaveItem {
        buildHeader()
        return this
    }

    override fun toJson(): String = encryptApiMessage(message = JSONArray().put(header.toJson()))

    override fun toObject(message: String): GoOnlineSlaveItem {
        header = parseToHeaderObject(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.GoOnline)
    }
}