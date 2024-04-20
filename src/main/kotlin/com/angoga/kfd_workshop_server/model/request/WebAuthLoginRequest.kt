package com.angoga.kfd_workshop_server.model.request

data class WebAuthLoginRequest(
    val email: String,
    val fingerprint: String, // fingerprint, определяющий public key
)