package com.angoga.kfd_workshop_server.service.impl.web_authn


import org.springframework.stereotype.Service
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher


@Service
object CryptoService {
    private fun loadPublicKey(storedKey: String): PublicKey {
//        FreelancingLogger(this::class.java).info("sadsa123")
        val data = Base64.getDecoder().decode(storedKey)
//        FreelancingLogger(this::class.java).info("sadsa")
        val spec = X509EncodedKeySpec(data)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePublic(spec)
    }

    fun code(challenge: String, publicKeyAsString: String) : String{
        val publicKey = loadPublicKey(publicKeyAsString)
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedData = cipher.doFinal(challenge.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encryptedData)
    }
}