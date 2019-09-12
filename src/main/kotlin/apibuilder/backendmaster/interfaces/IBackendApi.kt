package apibuilder.backendmaster.interfaces

import org.json.JSONArray
import org.json.JSONObject
import java.io.File

interface IBackendApi {
    fun executeOnlineUpdate(): JSONObject
    fun executeOfflineUpdate(): JSONObject
    fun getOnlineUpdates(): JSONObject
    fun enableAutoOnlineUpdate(isEnabled: Boolean): JSONObject
    fun enableAutoOfflineUpdate(isEnabled: Boolean): JSONObject
    fun isAutoOnlineUpdateEnabled(): JSONObject
    fun isAutoOfflineUpdateEnabled(): JSONObject
    fun getSlaveSoftwareUpdateMessage(file: File): JSONArray
}