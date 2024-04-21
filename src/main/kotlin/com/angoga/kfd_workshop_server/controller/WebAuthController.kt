package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.service.WebAuthService
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(API_VERSION_1)
class WebAuthController(
    @Autowired
    private val service: WebAuthService
) {

    @PostMapping("/web_auth/registration")
    fun webAuthRegister(@RequestBody request : WebAuthRegistrationRequest) = service.register(request)

    @PostMapping("/public/web_auth/login")
    fun webAuthLogin(@RequestBody request: WebAuthLoginRequest) = service.login(request)

    @PostMapping("/public/web_auth/check_access")
    fun webAuthLogin() = service.checkAccess()

    @PostMapping("/web_auth/grant_access")
    fun grantAccess(@RequestBody request: WebAuthGrantAccessRequest) = service.grantAccess(request)
}