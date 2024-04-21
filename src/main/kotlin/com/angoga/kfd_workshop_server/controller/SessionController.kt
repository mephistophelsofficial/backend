package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.service.SessionService
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(API_VERSION_1)
class SessionController(
    @Autowired
    private val service: SessionService,
) {
    @GetMapping("/web_auth/get_session_challenge/{id}")
    fun getSessionChallenge(@PathVariable id : Long) = service.getSessionChallenge(id)
}