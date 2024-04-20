package com.angoga.kfd_workshop_server.errors

import org.springframework.http.HttpStatus

class CorruptedTokenException : ApiError(
    status = HttpStatus.UNAUTHORIZED,
    message = "Войдите в аккаунт",
    debugMessage = "Log in to your account",
)
