package apibuilder.database.device.item

import apibuilder.RsaModule
import apibuilder.database.interfaces.IItem
import enumstorage.device.Device
import org.json.JSONObject

class DeviceItem: IItem {
    var id: Int = 0
    var isStandard: Boolean = false
    lateinit var deviceDe: String
    lateinit var deviceEn: String

    fun build(id: Int, isStandard: Boolean, deviceDe: String, deviceEn: String): DeviceItem {
        this.id = id
        this.isStandard = isStandard
        this.deviceDe = deviceDe
        this.deviceEn = deviceEn
        return this
    }
    
    override fun toJson(): JSONObject = JSONObject()
            .put(Device.Id.name, id)
            .put(Device.DeviceDe.name, deviceDe)
            .put(Device.DeviceEn.name, deviceEn)
            .put(Device.IsStandard.name, isStandard)

    override fun toObject(string: String): DeviceItem {
        val apiMessage = RsaModule.decryptApiMessage(message = string).last() as JSONObject
        return toObject(json = apiMessage)
    }

    override fun toObject(json: JSONObject): DeviceItem {
        id = if (json.has(Device.Id.name)) json.getInt(Device.Id.name) else 0
        deviceDe = if (json.has(Device.DeviceDe.name)) json.getString(Device.DeviceDe.name) else String()
        deviceEn = if (json.has(Device.DeviceEn.name)) json.getString(Device.DeviceEn.name) else String()
        isStandard = if (json.has(Device.IsStandard.name)) json.getBoolean(Device.IsStandard.name) else false
        return this
    }
}