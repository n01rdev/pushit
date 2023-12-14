package com.pushit.api.role.infrastructure.db.postgre.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
data class RoleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private val id: Long = 0,

    @Column(nullable = false, unique = true)
    private var uuid: String = "",

    @Column(nullable = false, unique = true) val name: String,
) {
    @PrePersist
    fun generateUUID() {
        this.uuid = UUID.randomUUID().toString() // TODO: Migrate to Value Objects
    }
}