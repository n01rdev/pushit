package com.pushit.api.security.application.service.user

import com.pushit.api.role.domain.repository.IRoleRepository
import com.pushit.api.security.application.response.AuthResponse
import com.pushit.api.security.domain.exception.UserAlreadyExistsException
import com.pushit.api.security.domain.model.User as UserModel
import com.pushit.api.security.domain.service.token.IJwtService
import com.pushit.api.security.domain.service.user.ICreateUserService
import com.pushit.api.security.infrastructure.db.postgre.repository.SecurityRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserService(
    private val securityRepository: SecurityRepository,
    private val roleRepository: IRoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: IJwtService,
): ICreateUserService {

    private val jweLogger = org.slf4j.LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun register(user: UserModel): AuthResponse {

        if (securityRepository.findByEmailEntity(user.email) != null) {
            throw UserAlreadyExistsException()
        }

        val defaultRole = if (user.roles.isEmpty()) {
            roleRepository.findByName("USER")
        } else {
            roleRepository.findByName(user.roles.first())
        }

        if (defaultRole != null) {
            user.roles = mutableListOf(defaultRole.name)
        }

        val userDetails: UserDetails = User.builder()
            .username(user.email)
            .password(passwordEncoder.encode(user.password))
            .roles(*user.roles.toTypedArray())
            .build()

        val userModel = UserModel(
            email = userDetails.username,
            password = userDetails.password,
            roles = userDetails.authorities.map { it.authority }.toMutableList()
        )

        defaultRole?.let { //TODO: Fix for adding relation in auth_role table
            userModel.roles += it.name
        }

        return try {
            jweLogger.info("Saving user: $user")
            val token = tokenService.generateToken(userDetails)
            val uuid = securityRepository.save(userModel)

            AuthResponse(
                token = token,
                uuid = uuid
            )
        } catch (e: RuntimeException) {
            jweLogger.error("Error while saving user", e)
            throw RuntimeException("Error while saving user", e)
        }
    }
}