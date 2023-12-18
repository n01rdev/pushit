package com.pushit.api.security.infrastructure.db.postgre.repository

import com.pushit.api.security.infrastructure.db.postgre.entity.AuthEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IJpaSecurityRepository: JpaRepository<AuthEntity, Int> {
    fun findByEmail(email: String): AuthEntity?
}