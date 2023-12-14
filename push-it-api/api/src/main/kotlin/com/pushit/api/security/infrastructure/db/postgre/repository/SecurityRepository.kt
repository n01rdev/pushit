package com.pushit.api.security.infrastructure.db.postgre.repository

import com.pushit.api.security.infrastructure.db.postgre.entity.AuthEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class SecurityRepository (
    @PersistenceContext
    private val entityManager: EntityManager,
    private val jpaRepository: IJpaSecurityRepository
) {

    fun save(authEntity: AuthEntity): String {
        entityManager.persist(authEntity)
        entityManager.flush()
        return authEntity.uuid
    }

    fun findByEmail(email: String): AuthEntity? {
        return jpaRepository.findByEmail(email)
    }
}