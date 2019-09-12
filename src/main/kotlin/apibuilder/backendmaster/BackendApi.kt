package apibuilder.backendmaster

import apibuilder.backendmaster.interfaces.IBackendApi
import enumstorage.api.ApiValue
import enumstorage.slave.SlaveCommand
import enumstorage.update.UpdateCommand
import enumstorage.update.UpdateCommandValues
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object BackendApi: IBackendApi {
    @Synchronized
    override fun executeOnlineUpdate(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.OnlineUpdate.name)

    @Synchronized
    override fun executeOfflineUpdate(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.UsbUpdate.name)

    @Synchronized
    override fun getOnlineUpdates(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.AvailableOnlineUpdates.name)

    @Synchronized
    override fun enableAutoOnlineUpdate(isEnabled: Boolean): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.EnableAutoOnlineUpdate.name)
            .put(UpdateCommandValues.Enabled.name, isEnabled)

    @Synchronized
    override fun enableAutoOfflineUpdate(isEnabled: Boolean): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.EnableAutoOfflineUpdate.name)
            .put(UpdateCommandValues.Enabled.name, isEnabled)

    @Synchronized
    override fun isAutoOnlineUpdateEnabled(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.IsAutoOnlineUpdateEnabled.name)

    @Synchronized
    override fun isAutoOfflineUpdateEnabled(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.IsAutoOfflineUpdateEnabled.name)

    @Synchronized
    override fun getSlaveSoftwareUpdateMessage(file: File): JSONArray {
        val header = JSONObject()
                .put(ApiValue.Command.name, SlaveCommand.UpdateSlave)

        val body = JSONObject()
                .put(ApiValue.File.name, file.readBytes())

        return JSONArray()
                .put(header)
                .put(body)
    }
}