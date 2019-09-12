package apibuilder

import org.json.JSONArray
import rsa.encryption.Encryption

object RsaModule {
    @Synchronized
    fun encryptApiMessage(message: JSONArray): String {
        return Encryption.encrypt(message = message.toString())
    }

    @Synchronized
    fun decryptApiMessage(message: String): JSONArray {
        return JSONArray(Encryption.decrypt(message = message))
    }
}