package com.pushit.api.core.config.application

import com.pushit.api.security.infrastructure.db.postgre.repository.SecurityRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationConfig(
    private val securityRepository: SecurityRepository
) {

    @Bean
    fun userDetailsService() : UserDetailsService {
        return UserDetailsService { email ->
            val user = securityRepository.findByEmailEntity(email)
            user?.let {
                User.withUsername(it.email)
                    .password(it.password)
                    .roles(*it.roles.map { role -> role.name }.toTypedArray())
                    .build()
            } ?: throw UsernameNotFoundException("User not found with email: $email")
        }
    }

    @Bean
    fun authenticationProvider() : AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration) : AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}