package com.pushit.api.security.infrastructure.db.postgre.repository

import com.pushit.api.security.application.mapper.DomainEntityMapper
import com.pushit.api.security.domain.model.User
import com.pushit.api.security.domain.repository.ISecurityRepository
import com.pushit.api.security.infrastructure.db.postgre.entity.AuthEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class SecurityRepository (
    @PersistenceContext
    private val entityManager: EntityManager,
    private val jpaRepository: IJpaSecurityRepository,
    private val mapper: DomainEntityMapper
): ISecurityRepository {
    override fun save(user: User): String {
        val authEntity = mapper.toEntity(user)
        entityManager.persist(authEntity)
        entityManager.flush()

        return authEntity.uuid
    }

    override fun findByEmail(email: String): User? {
        val authEntity = jpaRepository.findByEmail(email)
        return authEntity?.let { mapper.toDomain(it) }
    }

    fun findByEmailEntity(email: String): AuthEntity? {
        return jpaRepository.findByEmail(email)
    }
}