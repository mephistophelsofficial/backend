package com.angoga.kfd_workshop_server.model.request

import jakarta.validation.constraints.Size

data class WebAuthRegistrationRequest(
    @field:Size(min = 4, max = 255)
    val key: String,
    @field:Size(min = 4, max = 255)
    val fingerPrint: String,
)
