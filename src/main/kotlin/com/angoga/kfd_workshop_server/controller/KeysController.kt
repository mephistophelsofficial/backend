package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.service.KeysService
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(API_VERSION_1)
class KeysController(
    @Autowired
    private val service: KeysService
) {
    @GetMapping("/web_auth/get_keys")
    fun getKeys() = service.getKeys()

}