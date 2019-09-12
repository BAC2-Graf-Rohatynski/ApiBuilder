package apibuilder.backendmaster

import apibuilder.backendmaster.interfaces.ICommandBackendApi
import enumstorage.update.UpdateCommand
import enumstorage.update.UpdateCommandValues
import org.json.JSONObject

object CommandBackendApi: ICommandBackendApi {
    @Synchronized
    override fun usbUpdate(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.UsbUpdate.name)

    @Synchronized
    override fun onlineUpdate(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.OnlineUpdate.name)

    @Synchronized
    override fun availableOnlineUpdates(): JSONObject = JSONObject()
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
    override fun shutdown(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.Shutdown.name)

    @Synchronized
    override fun restart(): JSONObject = JSONObject()
            .put(UpdateCommandValues.Command.name, UpdateCommand.Restart.name)
}