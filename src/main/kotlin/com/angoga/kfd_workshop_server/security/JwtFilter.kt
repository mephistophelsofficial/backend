package com.angoga.kfd_workshop_server.security

import com.angoga.kfd_workshop_server.errors.ApiError
import com.angoga.kfd_workshop_server.errors.ExceptionResolver
import com.angoga.kfd_workshop_server.logging.FreelancingLogger
import com.angoga.kfd_workshop_server.util.API_PUBLIC
import com.angoga.kfd_workshop_server.util.WAITING_ROLE
import com.angoga.kfd_workshop_server.util.containsAnyPath
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.GrantedAuthority

@Component
class JwtFilter(
    @Lazy
    private val jwtParser: JwtParser,
    private val exceptionResolver: ExceptionResolver,
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requestURI.containsAnyPath(
            API_PUBLIC,
            "/h2-console"
        )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val header = request.getHeader("Authorization")
                ?: throw ApiError(HttpStatus.UNAUTHORIZED, "Вы не авторизованы","Authorization header not found")

            if(request.requestURI.containsAnyPath("waiting")){
                SecurityContextHolder.getContext().authentication = jwtParser.createWaitingAuthToken(header)
            }
            else{
                SecurityContextHolder.getContext().authentication = jwtParser.createAuthToken(header)
            }
            for(auth in SecurityContextHolder.getContext().authentication.authorities){
                FreelancingLogger(this.javaClass).info(auth.authority)
            }
            filterChain.doFilter(request, response)
        } catch (exception: ApiError) {
            exceptionResolver.resolveException(request, response, exception)
        }
    }
}
