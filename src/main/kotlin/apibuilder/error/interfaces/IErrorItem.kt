package apibuilder.error.interfaces

import apibuilder.ISocketApi

interface IErrorItem: ISocketApi {
    fun getRequestId(): Int
}
