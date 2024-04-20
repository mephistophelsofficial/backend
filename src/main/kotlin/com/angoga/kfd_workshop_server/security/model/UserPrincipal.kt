package com.angoga.kfd_workshop_server.security.model

import com.angoga.kfd_workshop_server.util.USER_ROLE
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority


class UserPrincipal(
    val userId: Long,
    val authorities: List<GrantedAuthority> = listOf(GrantedAuthority { USER_ROLE })
) : AbstractAuthenticationToken(authorities) {
    override fun getCredentials() = null
    override fun getPrincipal() = userId
}
