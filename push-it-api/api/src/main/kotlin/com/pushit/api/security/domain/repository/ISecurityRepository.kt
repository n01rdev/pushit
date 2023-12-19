package com.pushit.api.security.domain.repository

import com.pushit.api.security.domain.model.User

interface ISecurityRepository {
    fun save(user: User): String
    fun findByEmail(email: String): User?
    fun findByUuid(uuid: String): User?
}