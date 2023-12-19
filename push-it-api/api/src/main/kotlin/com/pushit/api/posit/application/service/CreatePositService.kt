package com.pushit.api.posit.application.service

import com.pushit.api.posit.domain.exception.AuthorNotFoundException
import com.pushit.api.posit.domain.exception.PositAlreadyExistsException
import com.pushit.api.posit.domain.model.Posit
import com.pushit.api.posit.domain.notifier.IPostNotifier
import com.pushit.api.posit.domain.service.ICreatePositService
import com.pushit.api.posit.infrastructure.db.postgres.repository.PositRepository
import com.pushit.api.posit.application.mapper.PositMapper
import com.pushit.api.security.infrastructure.db.postgre.repository.SecurityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreatePositService(
    private val positRepository: PositRepository,
    private val securityRepository: SecurityRepository,
    private val positMapper: PositMapper,
    private val postNotifier: IPostNotifier
) : ICreatePositService {

    @Transactional
    override fun create(posit: Posit): String {
        val existingAuthor = securityRepository.findByUuidEntity(posit.authorUuid)
        val existingPosit = positRepository.findByTitle(posit.title)

        if (existingAuthor == null) {
            throw AuthorNotFoundException()
        }

        if (existingPosit != null) {
            throw PositAlreadyExistsException()
        }

        val savedPosit = positRepository.save(posit)

        val positEntity = positMapper.toEntity(savedPosit)

        postNotifier.notifyNewPost(savedPosit)

        return positEntity.uuid
    }
}
