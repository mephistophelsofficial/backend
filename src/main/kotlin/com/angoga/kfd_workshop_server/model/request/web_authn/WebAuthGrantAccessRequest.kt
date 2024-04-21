package com.angoga.kfd_workshop_server.model.request.web_authn

data class WebAuthGrantAccessRequest(
    val sessionId : String,
    val solvedChallenge : String,
)
