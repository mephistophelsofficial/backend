package com.angoga.kfd_workshop_server.util

import com.angoga.kfd_workshop_server.security.model.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

// returns user id
fun getPrincipal(): Long = (SecurityContextHolder.getContext().authentication.principal as Long)
