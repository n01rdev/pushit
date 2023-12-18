package com.pushit.api.core.config.db

import com.pushit.api.role.domain.repository.IRoleRepository
import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InitDB {

    @Bean
    fun init(roleRepository: IRoleRepository): CommandLineRunner {
        return CommandLineRunner { _ ->
            if (roleRepository.findByName("ROLE_USER") == null) {
                roleRepository.save(RoleEntity(name = "ROLE_USER"))
            }
            if (roleRepository.findByName("ROLE_ADMIN") == null) {
                roleRepository.save(RoleEntity(name = "ROLE_ADMIN"))
            }
        }
    }
}