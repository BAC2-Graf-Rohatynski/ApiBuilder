package apibuilder.database.header

import apibuilder.randomgenerator.RandomGenerator
import enumstorage.api.ApiValue
import enumstorage.database.DatabaseCommand
import enumstorage.database.DatabaseType
import org.json.JSONObject

class Header: IHeader  {
    lateinit var command: String
    lateinit var table: String
    var requestId: Int = 0
    var socketId: Int = 0

    fun build(table: DatabaseType, command: DatabaseCommand, requestId: Int? = null, socketId: Int? = null): Header {
        this.command = command.name
        this.table = table.name
        this.requestId = requestId ?: RandomGenerator.bigRandom()
        this.socketId = socketId ?: 0
        return this
    }

    fun build(table: String, command: String, requestId: Int? = null, socketId: Int? = null): Header {
        this.command = command
        this.table = table
        this.requestId = requestId ?: RandomGenerator.bigRandom()
        this.socketId = socketId ?: 0
        return this
    }

    override fun toJson(): JSONObject {
        if (!::command.isInitialized || !::table.isInitialized) {
            throw Exception("Object not initialized!")
        }

        return JSONObject()
                .put(ApiValue.Command.name, command)
                .put(ApiValue.Table.name, table)
                .put(ApiValue.RequestId.name, requestId)
                .put(ApiValue.SocketId.name, socketId)
    }
}