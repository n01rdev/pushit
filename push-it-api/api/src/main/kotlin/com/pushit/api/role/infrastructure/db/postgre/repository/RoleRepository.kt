package com.pushit.api.role.infrastructure.db.postgre.repository

import com.pushit.api.role.domain.repository.IRoleRepository
import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RoleRepository(
    @PersistenceContext
    private val entityManager: EntityManager,
    private val jpaRepository: IJpaRoleRepository
): IRoleRepository {

    @Transactional
    override fun save(role: RoleEntity) {
        entityManager.persist(role)
        entityManager.flush()
    }

    override fun findByName(name: String): RoleEntity? {
        return jpaRepository.findByName(name)
    }
}