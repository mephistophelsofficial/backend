package com.angoga.kfd_workshop_server.model.request

import com.angoga.kfd_workshop_server.util.EMAIL_REGEX
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class RegistrationRequest(
    @field:Pattern(regexp = EMAIL_REGEX)
    @field:Size(min = 4, max = 120)
    val email: String,
    @field:Size(min = 8, max = 255)
    val password: String,
    @field:Size(max = 255)
    val name: String,
)