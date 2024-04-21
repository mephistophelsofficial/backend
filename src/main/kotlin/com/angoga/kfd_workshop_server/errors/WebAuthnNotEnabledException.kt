package com.angoga.kfd_workshop_server.errors

import org.springframework.http.HttpStatus

class WebAuthnNotEnabledException : ApiError(message = "WebAuthn is not enabled at this account!", status = HttpStatus.BAD_REQUEST, debugMessage = "WebAuthn is not enabled at this account!")