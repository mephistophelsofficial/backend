package com.angoga.kfd_workshop_server

import com.angoga.kfd_workshop_server.service.impl.auth.CryptoService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class KfdWorkshopServerApplicationTests {
	@Test
	fun cryptoTest() {
		val service = CryptoService
		val helper = CryptoHelper

		val kp = helper.generateKeyPair()

		val testString = "SomeTestString12345678"

		val coded = service.code(testString, helper.publicKeyToString(kp.public))

		assert(helper.decodeString(kp.private, coded) == testString)
	}

}
