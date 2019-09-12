package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.api.ApiValue
import enumstorage.slave.SlaveCommand
import org.json.JSONArray
import org.json.JSONObject

class RenameShowItem: ISlaveRequestItem, SlaveRequest() {
    lateinit var show: String

    fun create(show: String): RenameShowItem {
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

    override fun toObject(message: String): RenameShowItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        show = body.getString(ApiValue.Show.name)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.EnableShow)
    }
}