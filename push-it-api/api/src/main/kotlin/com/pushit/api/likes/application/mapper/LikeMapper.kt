package com.pushit.api.likes.application.mapper

import com.pushit.api.likes.domain.model.Like
import com.pushit.api.likes.infrastructure.db.postgres.entity.LikeEntity
import com.pushit.api.posit.domain.exception.AuthorNotFoundException
import com.pushit.api.posit.domain.exception.PositNotFoundException
import com.pushit.api.posit.infrastructure.db.postgres.repository.PositRepository
import com.pushit.api.security.infrastructure.db.postgre.repository.SecurityRepository
import org.springframework.stereotype.Component

@Component
class LikeMapper(
    private val securityRepository: SecurityRepository,
    private val positRepository: PositRepository
) {

    fun toEntity(like: Like): LikeEntity {
        val author = securityRepository.findByUuidEntity(like.userUuid)
        val posit = positRepository.findByUuidEntity(like.positUuid)

        if (author == null) {
            throw AuthorNotFoundException()
        }

        if (posit == null) {
            throw PositNotFoundException()
        }

        return LikeEntity(user = author, posit = posit)
    }

    fun toModel(likeEntity: LikeEntity): Like {
        val author = securityRepository.findByUuidEntity(likeEntity.user.uuid)?: throw AuthorNotFoundException()
        val posit = positRepository.findByUuidEntity(likeEntity.posit.uuid)?: throw PositNotFoundException()
        return Like(
            userUuid = author.uuid,
            positUuid = posit.uuid
        )
    }
}