package com.pushit.api.role.infrastructure.db.postgre.repository

import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IJpaRoleRepository: JpaRepository<RoleEntity, Int> {
    fun findByName(name: String): RoleEntity?
}