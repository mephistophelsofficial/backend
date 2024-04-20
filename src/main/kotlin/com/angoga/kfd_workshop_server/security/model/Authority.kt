package com.angoga.kfd_workshop_server.security.model

import org.springframework.security.core.GrantedAuthority
import com.angoga.kfd_workshop_server.util.*

enum class Authority(val authority: GrantedAuthority) {
    USER(GrantedAuthority { USER_ROLE })
}