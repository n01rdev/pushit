package com.pushit.api.core.config.db

import com.pushit.api.role.infrastructure.db.postgre.entity.RoleEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InitDB {

    @Bean
    fun init(roleRepository: IRoleRepository): CommandLineRunner {
        return CommandLineRunner { _ ->
            if (roleRepository.findByName("User") == null) {
                roleRepository.save(RoleEntity(name = "User"))
            }
            if (roleRepository.findByName("Admin") == null) {
                roleRepository.save(RoleEntity(name = "Admin"))
            }
        }
    }
}