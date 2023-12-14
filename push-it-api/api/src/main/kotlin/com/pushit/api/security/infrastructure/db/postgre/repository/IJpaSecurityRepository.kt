package com.pushit.api.security.infrastructure.db.postgre.repository

import com.pushit.api.security.infrastructure.db.postgre.entity.AuthEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IJpaSecurityRepository: JpaRepository<AuthEntity, Int> {
    fun findByEmail(email: String): AuthEntity?
}