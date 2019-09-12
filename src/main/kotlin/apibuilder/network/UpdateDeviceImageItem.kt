package apibuilder.network

import apibuilder.database.image.item.ImageItem
import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.image.Image
import enumstorage.network.NetworkCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateDeviceImageItem: INetworkItem, NetworkResponse() {
    lateinit var ipAddress: String
    lateinit var item: ImageItem

    fun create(item: ImageItem, ipAddress: String): UpdateDeviceImageItem {
        this.ipAddress = ipAddress
        this.item = item
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = item.toJson().put(SlaveInformation.IpAddress.name, ipAddress)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateDeviceImageItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(SlaveInformation.IpAddress.name)

        item = ImageItem().build(
                id = body.getInt(Image.Id.name),
                imageDe = body.getString(Image.ImageDe.name),
                imageEn = body.getString(Image.ImageEn.name),
                isStandard = body.getBoolean(Image.IsStandard.name),
                fileStream = body.getString(Image.FileStream.name).toByteArray(),
                fileName = body.getString(Image.FileName.name)
        )

        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.UpdateDeviceImage)
    }
}