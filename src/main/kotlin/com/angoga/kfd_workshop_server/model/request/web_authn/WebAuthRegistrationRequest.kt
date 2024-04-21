package com.angoga.kfd_workshop_server.model.request.web_authn

data class WebAuthRegistrationRequest(
    val publicKey: String,
    val privateKey: String,
)
