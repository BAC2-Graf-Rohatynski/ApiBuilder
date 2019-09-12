package apibuilder.slave.response.header

import enumstorage.api.ApiValue
import enumstorage.slave.SlaveResponse
import org.json.JSONObject

class Header: IHeader {
    lateinit var command: String

    fun build(command: SlaveResponse): Header {
        this.command = command.name
        return this
    }

    fun build(command: String): Header {
        this.command = command
        return this
    }

    override fun toJson(): JSONObject {
        if (!::command.isInitialized) {
            throw Exception("Object not initialized!")
        }

        return JSONObject().put(ApiValue.Command.name, command)
    }
}