package com.pushit.api.posit.presentation.rest.v1

import com.pushit.api.posit.application.service.CreatePositService
import com.pushit.api.posit.domain.exception.AuthorNotFoundException
import com.pushit.api.posit.domain.exception.PositAlreadyExistsException
import com.pushit.api.posit.domain.model.Posit
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CreatePositController(
    private val createPositService: CreatePositService
) {
    @PostMapping("/posit")
    fun create(@RequestBody posit: Posit) : ResponseEntity<String> {
        return try {
            val uuid = createPositService.create(posit)
            ResponseEntity.status(HttpStatus.CREATED).body(uuid)
        } catch (e: AuthorNotFoundException) {
            ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.message)
        } catch (e: PositAlreadyExistsException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(e.message)
        }
    }

}
