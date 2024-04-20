package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.model.request.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.service.WebAuthService
import com.angoga.kfd_workshop_server.util.API_PUBLIC
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(API_VERSION_1)
class WebAuthController(
    @Autowired
    private val service: WebAuthService
) {

    @PostMapping("/web_auth/register")
    fun webAuthRegister(@RequestBody request : WebAuthRegistrationRequest) = service.register(request)

    @GetMapping("/public/web_auth/fingerprints/{email}")
    fun getFingerprints(@PathVariable email : String) = service.getFingerprints(email)

    @PostMapping("/public/web_auth/login")
    fun webAuthLogin(@RequestBody request: WebAuthLoginRequest) = service.login(request)

    @PostMapping("/public/web_auth/check_access")
    fun webAuthLogin() = service.checkAccess()

    @PostMapping("/web_auth/grant_access")
    fun grantAccess(@RequestBody request: WebAuthGrantAccessRequest) = service.grantAccess(request)

}