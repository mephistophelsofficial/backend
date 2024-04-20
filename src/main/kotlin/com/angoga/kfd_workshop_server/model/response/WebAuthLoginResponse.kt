package com.angoga.kfd_workshop_server.model.response

data class WebAuthLoginResponse(
    val sessionId: String,
    val challenge: String,
    val checkJwt : String, // нужно для запросов на /public/web_auth/check_access
)