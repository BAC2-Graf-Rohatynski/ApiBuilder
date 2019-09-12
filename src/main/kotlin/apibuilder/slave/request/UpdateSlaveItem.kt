package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.api.ApiValue
import enumstorage.slave.SlaveCommand
import org.json.JSONArray
import org.json.JSONObject

class UpdateSlaveItem: ISlaveRequestItem, SlaveRequest() {
    lateinit var item: ByteArray

    fun create(item: ByteArray): UpdateSlaveItem {
        this.item = item
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject().put(ApiValue.File.name, item.toString())

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateSlaveItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        item = body.getString(ApiValue.File.name).toByteArray()
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.UpdateDeviceImage)
    }
}