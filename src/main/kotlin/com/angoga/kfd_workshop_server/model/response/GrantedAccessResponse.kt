package com.angoga.kfd_workshop_server.model.response

import java.security.PrivateKey

data class GrantedAccessResponse(
    val accessJwt: String,
    val privateKey: String,
)
