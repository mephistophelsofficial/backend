package com.angoga.kfd_workshop_server.security

import com.angoga.kfd_workshop_server.util.API_PUBLIC
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val corsConfig: CorsConfigurationSource
) {

    @Bean
    fun getEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.cors { it.configurationSource(corsConfig) }
        http.csrf { csrf -> csrf.disable() }

        http.authorizeHttpRequests { requests ->
            requests.requestMatchers("$API_PUBLIC/web_auth/check_access").hasAnyRole("WAITING")
            requests.requestMatchers("$API_PUBLIC/**").permitAll()
            requests.requestMatchers("$API_VERSION_1/**").hasAnyRole("USER")
        }

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        http.authenticationManager(authManager(http))

        return http.build()
    }

    @Bean
    fun authManager(http: HttpSecurity): AuthenticationManager? {
        val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        builder.inMemoryAuthentication()
        return builder.build()
    }
}