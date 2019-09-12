package apibuilder.slave

import org.json.JSONObject

interface ISlave {
    fun build(message: String): Slave
    fun build(message: JSONObject): Slave
    fun toJson(): JSONObject
    fun parseGreeting(message: String): Slave
    fun parseGeo(message: String): Slave
    fun parseError(message: String): Slave
    fun parseConnect(message: String): Slave
    fun parseFirstConnect(message: String): Slave
    fun parseRead(message: String): Slave
}