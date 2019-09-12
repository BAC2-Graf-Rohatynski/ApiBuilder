package apibuilder

interface ISocketApi {
    fun toJson(): String
    fun toObject(message: String): ISocketApi
    fun buildHeader()
}