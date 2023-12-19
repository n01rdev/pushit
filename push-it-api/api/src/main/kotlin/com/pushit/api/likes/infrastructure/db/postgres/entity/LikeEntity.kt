package com.pushit.api.likes.infrastructure.db.postgres.entity

import com.pushit.api.posit.infrastructure.db.postgres.entity.PositEntity
import com.pushit.api.security.infrastructure.db.postgre.entity.AuthEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "user_likes",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_uuid", "posit_uuid"])
    ]
)
data class LikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_id_seq")
    private val id: Long = 0,

    @Column(nullable = false, unique = true)
    val uuid: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    val user: AuthEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posit_uuid", referencedColumnName = "uuid", nullable = false)
    val posit: PositEntity
) {
    @PrePersist
    fun addLikeToPosit() {
        posit.addLike(this)
    }

    @PreRemove
    fun removeLikeFromPosit() {
        posit.removeLike(this)
    }
}