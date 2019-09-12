package apibuilder.license.header

import enumstorage.api.ApiValue
import org.json.JSONObject

class Header: IHeader {
    lateinit var command: String

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