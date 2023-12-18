package com.pushit.api.security.presentation.rest.v1

import com.pushit.api.security.application.mapper.RequestDomainMapper
import com.pushit.api.security.application.request.AuthRequest
import com.pushit.api.security.application.response.AuthResponse
import com.pushit.api.security.domain.exception.UserAlreadyExistsException
import com.pushit.api.security.domain.service.user.ICreateUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/security")
class CreateUserController(
    private val createUserService: ICreateUserService,
    private val requestMapper: RequestDomainMapper
) {

    @PostMapping("/register")
    fun register(
        @RequestBody
        request: AuthRequest
    ): ResponseEntity<Any> {
        return try {
            val response: AuthResponse = createUserService.register(requestMapper.toDomain(request))
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (e: UserAlreadyExistsException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        } catch (e: RuntimeException) {
            ResponseEntity.internalServerError().body(e.message)
        }
    }
}
