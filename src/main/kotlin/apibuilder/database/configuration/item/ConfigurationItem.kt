package apibuilder.database.configuration.item

import apibuilder.RsaModule
import apibuilder.database.interfaces.IItem
import enumstorage.slave.SlaveInformation
import org.json.JSONObject

class ConfigurationItem: IItem {
    private var ssid: Int = 0
    private lateinit var ddfHash: String
    private lateinit var ddfFile: String

    fun build(ssid: Int, ddfHash: String, ddfFile: String): IItem {
        this.ssid = ssid
        this.ddfHash = ddfHash
        this.ddfFile = ddfFile
        return this
    }

    override fun toJson(): JSONObject = JSONObject()
            .put(SlaveInformation.Ssid.name, ssid)
            .put(SlaveInformation.DdfHash.name, ddfHash)
            .put(SlaveInformation.DdfFile.name, ddfFile)

    override fun toObject(string: String): ConfigurationItem {
        val apiMessage = RsaModule.decryptApiMessage(message = string).last() as JSONObject
        return toObject(json = apiMessage)
    }

    override fun toObject(json: JSONObject): ConfigurationItem {
        ssid = if (json.has(SlaveInformation.Ssid.name)) json.getInt(SlaveInformation.Ssid.name) else 0
        ddfHash = if (json.has(SlaveInformation.DdfHash.name)) json.getString(SlaveInformation.DdfHash.name) else String()
        ddfFile = if (json.has(SlaveInformation.DdfFile.name)) json.getString(SlaveInformation.DdfFile.name) else String()
        return this
    }
}