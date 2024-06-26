package com.angoga.kfd_workshop_server.model.response.user

import com.angoga.kfd_workshop_server.model.response.common.AbstractResponse

open class UserResponse(
    id: Long,
    val email: String,
    val name: String,

): AbstractResponse(id)