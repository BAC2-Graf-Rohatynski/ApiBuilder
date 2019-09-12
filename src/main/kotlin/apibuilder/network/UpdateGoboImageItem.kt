package apibuilder.network

import apibuilder.database.gobo.item.GoboItem
import apibuilder.network.header.Header
import apibuilder.network.header.NetworkResponse
import apibuilder.network.interfaces.INetworkItem
import enumstorage.gobo.Gobo
import enumstorage.network.NetworkCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateGoboImageItem: INetworkItem, NetworkResponse() {
    lateinit var ipAddress: String
    lateinit var item: GoboItem

    fun create(item: GoboItem, ipAddress: String): UpdateGoboImageItem {
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

    override fun toObject(message: String): UpdateGoboImageItem {
        header = parseToHeaderObject(message = message)

        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(SlaveInformation.IpAddress.name)

        item = GoboItem().build(
                id = body.getInt(Gobo.Id.name),
                goboDe = body.getString(Gobo.GoboDe.name),
                goboEn = body.getString(Gobo.GoboEn.name),
                isStandard = body.getBoolean(Gobo.IsStandard.name),
                fileStream = body.getString(Gobo.FileStream.name).toByteArray(),
                fileName = body.getString(Gobo.FileName.name)
        )

        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = NetworkCommand.UpdateGoboImage)
    }
}