package com.angoga.kfd_workshop_server.service.impl.web_authn

import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.model.response.KeysResponse
import com.angoga.kfd_workshop_server.service.KeysService
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class KeysServiceImpl(
    @Autowired
    private val userService: UserService,
) : KeysService {
    override fun getKeys(): KeysResponse {
        val key = userService.findEntityById(getPrincipal()).key ?: throw ResourceNotFoundException()
        return KeysResponse(public = key.publicKey, private = key.privateKey)
    }
}