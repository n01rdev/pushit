package com.pushit.api.security.infrastructure.db.postgre.entity

import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "auth")
data class AuthEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_id_seq")
    private val id: Long = 0,

    @Column(nullable = false, unique = true)
    val uuid: String = UUID.randomUUID().toString(), // TODO: Move to Value Object | No time for implementing in demo

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    private val password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "auth_role",
        joinColumns = [JoinColumn(name = "auth_uuid", referencedColumnName = "uuid")],
        inverseJoinColumns = [JoinColumn(name = "role_uuid", referencedColumnName = "uuid")]
    )
    val roles: Set<RoleEntity> = HashSet()

) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}
