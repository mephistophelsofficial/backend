package com.angoga.kfd_workshop_server.mappers

import com.angoga.kfd_workshop_server.database.entity.Publication
import com.angoga.kfd_workshop_server.database.repository.LikeDao
import com.angoga.kfd_workshop_server.model.request.PublicationRequest
import com.angoga.kfd_workshop_server.model.response.PublicationResponse
import com.angoga.kfd_workshop_server.model.response.common.PageResponse
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component


@Component
class PublicationMapper(
    private val likeDao: LikeDao
) {
    fun asEntity(request: PublicationRequest): Publication {
        return Publication(
            request.title,
            request.content
        )
    }
    fun asNullableResponse(entity: Publication?): PublicationResponse? {
        return if (entity == null) null else asResponse(entity)
    }

    fun asResponse(entity: Publication): PublicationResponse {
        return PublicationResponse(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            isLiked = likeDao.existsByUserIdAndPublication(getPrincipal(), entity),
            likesCount = likeDao.countByPublication(entity)
        )
    }

    fun asPageResponse(page: Page<Publication>): PageResponse<PublicationResponse> {
        return PageResponse.build(page, ::asResponse)
    }
}