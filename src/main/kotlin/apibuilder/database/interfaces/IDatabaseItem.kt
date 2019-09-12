package apibuilder.database.interfaces

import apibuilder.ISocketApi

interface IDatabaseItem: ISocketApi {
    fun getRequestId(): Int
}