package com.angoga.kfd_workshop_server.model.response.web_authn

data class KeysResponse(
    val public: String,
    val private: String,
)
