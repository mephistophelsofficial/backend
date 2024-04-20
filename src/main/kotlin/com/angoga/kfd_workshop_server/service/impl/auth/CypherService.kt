package com.angoga.kfd_workshop_server.service.impl.auth


import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher


object CryptoService {
    private fun loadPublicKey(storedKey: String): PublicKey {
        val data = Base64.getDecoder().decode(storedKey)
        val spec = PKCS8EncodedKeySpec(data)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePublic(spec)
    }

    fun code(challenge: String, publicKeyAsString: String) : String{
        val publicKey = loadPublicKey(publicKeyAsString)
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(challenge.toByteArray()).toString()
    }
}