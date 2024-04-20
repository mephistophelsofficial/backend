package com.angoga.kfd_workshop_server.model.request

data class WebAuthGrantAccessRequest(
    val sessionId : String,
    val solvedChallenge : String,
)
