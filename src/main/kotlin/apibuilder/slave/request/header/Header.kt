package apibuilder.slave.request.header

import enumstorage.api.ApiValue
import enumstorage.slave.SlaveCommand
import org.json.JSONObject

class Header: IHeader {
    lateinit var command: String

    fun build(command: SlaveCommand): Header {
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