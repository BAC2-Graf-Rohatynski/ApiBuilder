package apibuilder.database.device

import apibuilder.database.device.item.DeviceItem
import apibuilder.database.header.DatabaseResponse
import apibuilder.database.header.Header
import apibuilder.database.interfaces.IDevice
import enumstorage.device.Device
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONArray
import org.json.JSONObject

class DeleteDeviceItem: IDevice, DatabaseResponse() {
    lateinit var item: DeviceItem

    fun create(item: DeviceItem): DeleteDeviceItem {
        this.item = item
        buildHeader()
        return this
    }

    override fun getRequestId(): Int = header.requestId

    override fun toJson(): String {
        val body = item.toJson()

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): DeleteDeviceItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        item = DeviceItem().build(
                id = body.getInt(Device.Id.name),
                deviceDe = body.getString(Device.DeviceDe.name),
                deviceEn = body.getString(Device.DeviceEn.name),
                isStandard = body.getBoolean(Device.IsStandard.name)
        )
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(
                command = DatabaseCommand.DeleteDevice,
                table = DatabaseType.Device
        )
    }
}