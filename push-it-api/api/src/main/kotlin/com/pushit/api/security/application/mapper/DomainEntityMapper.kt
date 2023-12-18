package com.pushit.api.security.application.mapper

import com.pushit.api.role.domain.repository.IRoleRepository
import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity
import com.pushit.api.security.domain.model.User
import com.pushit.api.security.infrastructure.db.postgre.entity.AuthEntity
import org.springframework.stereotype.Component

@Component
class DomainEntityMapper(
    private val roleRepository: IRoleRepository

) {
    fun toDomain(authEntity: AuthEntity): User {
        return User(
            email = authEntity.username,
            password = authEntity.password,
            roles = authEntity.authorities.map { it.authority }
        )
    }

    fun toEntity(user: User): AuthEntity {
        val roleEntities = getRoleEntity(user.roles)
        return AuthEntity(
            email = user.email,
            password = user.password,
            roles = roleEntities.toSet()
        )
    }

    private fun getRoleEntity(roleNames: List<String>): List<RoleEntity> {
        return roleNames.map { roleName ->
            val roleEntity = roleRepository.findByName(roleName)
            roleEntity ?: throw RuntimeException("Role not found: $roleName")
        }
    }
}