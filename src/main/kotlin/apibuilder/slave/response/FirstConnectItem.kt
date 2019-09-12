package apibuilder.slave.response

import apibuilder.slave.Slave
import apibuilder.slave.response.header.Header
import apibuilder.slave.response.header.SlaveResponse
import apibuilder.slave.response.interfaces.ISlaveResponseItem
import org.json.JSONArray

class FirstConnectItem: ISlaveResponseItem, SlaveResponse() {
    lateinit var slave: Slave

    fun create(slave: Slave): FirstConnectItem {
        this.slave = slave
        buildHeader()
        return this
    }

    override fun toJson(): String = encryptApiMessage(message = JSONArray()
            .put(header.toJson())
            .put(slave.toJson()))

    override fun toObject(message: String): FirstConnectItem {
        header = parseToHeaderObject(message = message)
        slave = Slave().parseConnect(message = message)
        return this
    }

    override fun buildHeader() {
        this.header = Header().build(command = enumstorage.slave.SlaveResponse.FirstConnect)
    }
}