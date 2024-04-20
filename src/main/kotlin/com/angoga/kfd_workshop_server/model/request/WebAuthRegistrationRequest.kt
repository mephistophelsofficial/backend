package com.angoga.kfd_workshop_server.model.request

import jakarta.validation.constraints.Size

data class WebAuthRegistrationRequest(
    val publicKey: String,
    val privateKey: String,
)
