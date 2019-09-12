package apibuilder

import enumstorage.update.ApplicationName
import org.json.JSONObject

object ApiBuilderRunner {
    fun getUpdateInformation(): JSONObject = UpdateInformation.getAsJson(applicationName = ApplicationName.Api.name)
}