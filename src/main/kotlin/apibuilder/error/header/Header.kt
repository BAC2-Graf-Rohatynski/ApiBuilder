package apibuilder.error.header

import apibuilder.randomgenerator.RandomGenerator
import enumstorage.api.ApiValue
import enumstorage.message.MessageCommand
import org.json.JSONObject

class Header: IHeader {
    lateinit var command: String
    var requestId: Int = 0
    var socketId: Int = 0

    fun build(command: MessageCommand, requestId: Int? = null, socketId: Int? = null): Header {
        this.command = command.name
        this.requestId = requestId ?: RandomGenerator.bigRandom()
        this.socketId = socketId ?: 0
        return this
    }

    fun build(command: String, requestId: Int? = null, socketId: Int? = null): Header {
        this.command = command
        this.requestId = requestId ?: RandomGenerator.bigRandom()
        this.socketId = socketId ?: 0
        return this
    }

    override fun toJson(): JSONObject {
        if (!::command.isInitialized) {
            throw Exception("Object not initialized!")
        }

        return JSONObject()
                .put(ApiValue.Command.name, command)
                .put(ApiValue.RequestId.name, requestId)
                .put(ApiValue.SocketId.name, socketId)
    }
}