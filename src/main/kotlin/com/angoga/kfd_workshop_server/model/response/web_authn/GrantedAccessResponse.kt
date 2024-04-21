package com.angoga.kfd_workshop_server.model.response.web_authn


data class GrantedAccessResponse(
    val accessJwt: String,
    val privateKey: String,
)
