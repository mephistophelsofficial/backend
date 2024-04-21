package com.angoga.kfd_workshop_server.util

import org.springframework.security.core.context.SecurityContextHolder

// returns user id
fun getPrincipal(): Long {
    println(SecurityContextHolder.getContext().authentication.principal)
    return SecurityContextHolder.getContext().authentication.principal as Long
}
