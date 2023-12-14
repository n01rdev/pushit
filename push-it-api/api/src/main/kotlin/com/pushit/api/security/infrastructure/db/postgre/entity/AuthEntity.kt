package com.pushit.api.security.infrastructure.db.postgre.entity

import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "auth")
data class AuthEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_id_seq")
    private val id: Long,

    @Column(nullable = false, unique = true) val uuid: String,

    @Column(nullable = false, unique = true)
    private val email: String,

    @Column(nullable = false)
    private val password: String,

    @ManyToOne
    @JoinColumn(name = "role_uuid", referencedColumnName = "uuid", nullable = false)
    private val role: RoleEntity

) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
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
