package apibuilder.slave.request

import apibuilder.slave.request.header.Header
import apibuilder.slave.request.header.SlaveRequest
import apibuilder.slave.request.interfaces.ISlaveRequestItem
import enumstorage.image.Image
import enumstorage.slave.SlaveCommand
import enumstorage.slave.SlaveInformation
import org.json.JSONArray
import org.json.JSONObject

class UpdateGoboImageSlaveItem: ISlaveRequestItem, SlaveRequest() {
    lateinit var fileName: String
    lateinit var fileStream: ByteArray
    lateinit var ipAddress: String

    fun create(fileName: String, fileStream: ByteArray, ipAddress: String): UpdateGoboImageSlaveItem {
        this.fileName = fileName
        this.fileStream = fileStream
        this.ipAddress = ipAddress
        buildHeader()
        return this
    }

    override fun toJson(): String {
        val body = JSONObject()
                .put(SlaveInformation.IpAddress.name, ipAddress)
                .put(Image.FileName.name, fileName)
                .put(Image.FileStream.name, fileName)

        return encryptApiMessage(message = JSONArray()
                .put(header.toJson())
                .put(body))
    }

    override fun toObject(message: String): UpdateGoboImageSlaveItem {
        header = parseToHeaderObject(message = message)
        val body = parseToBodyObject(message = message).last() as JSONObject
        ipAddress = body.getString(SlaveInformation.IpAddress.name)
        fileName = body.getString(Image.FileName.name)
        fileStream = body.get(Image.FileStream.name).toString().toByteArray()
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = SlaveCommand.UpdateGoboImage)
    }
}