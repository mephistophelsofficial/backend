package com.angoga.kfd_workshop_server

import com.angoga.kfd_workshop_server.logging.FreelancingLogger
import com.angoga.kfd_workshop_server.service.impl.web_authn.CryptoService
import org.junit.jupiter.api.Test

class KfdWorkshopServerApplicationTests {
	@Test
	fun cryptoTest() {
		val service = CryptoService
		val helper = CryptoHelper

		val kp = helper.generateKeyPair()

		val logger = FreelancingLogger(this.javaClass)

		logger.info(helper.publicKeyToString(kp.public))

		logger.info(helper.privateKeyToString(kp.private))

		val testString = "SomeTestString12345678"

		val coded = service.code(testString, helper.publicKeyToString(kp.public))

		assert(helper.decodeString(kp.private, coded) == testString)
	}

}
