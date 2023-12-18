package com.pushit.api.role.domain.repository

import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity // TODO: Implement Mapper domain layer being agnostic

interface IRoleRepository {
    fun save(role: RoleEntity)
    fun findByName(name: String): RoleEntity?
}