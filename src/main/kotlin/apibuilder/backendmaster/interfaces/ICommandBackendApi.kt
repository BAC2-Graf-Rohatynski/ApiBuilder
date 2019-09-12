package apibuilder.backendmaster.interfaces

import org.json.JSONObject

interface ICommandBackendApi {
    fun usbUpdate(): JSONObject
    fun onlineUpdate(): JSONObject
    fun availableOnlineUpdates(): JSONObject
    fun enableAutoOnlineUpdate(isEnabled: Boolean): JSONObject
    fun enableAutoOfflineUpdate(isEnabled: Boolean): JSONObject
    fun isAutoOnlineUpdateEnabled(): JSONObject
    fun isAutoOfflineUpdateEnabled(): JSONObject
    fun shutdown(): JSONObject
    fun restart(): JSONObject
}