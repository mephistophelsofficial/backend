package com.angoga.kfd_workshop_server.errors

class SessionExpiredException : ApiError(
    message = "Session expired!"
)