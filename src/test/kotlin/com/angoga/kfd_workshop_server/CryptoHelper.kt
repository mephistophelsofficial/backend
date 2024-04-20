package com.angoga.kfd_workshop_server

import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher


object CryptoHelper {

    fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize (2048)
        return keyPairGenerator.generateKeyPair()
    }

    fun decodeString(key: PrivateKey, data: String): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init (Cipher.DECRYPT_MODE, key)
        val decodedData = Base64.getDecoder().decode(data)
        val decryptedData = cipher.doFinal(decodedData)
        return String(decryptedData, Charsets.UTF_8)
    }

    fun privateKeyToString(privateKey: PrivateKey): String {
        val keyBytes = privateKey.encoded
        return Base64.getEncoder().encodeToString(keyBytes)
    }

    fun publicKeyToString(publicKey: PublicKey): String {
        val keyBytes = publicKey.encoded
        return Base64.getEncoder().encodeToString(keyBytes)
    }

}
