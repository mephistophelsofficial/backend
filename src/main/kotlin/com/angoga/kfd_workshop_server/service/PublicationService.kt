package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.database.entity.Publication
import com.angoga.kfd_workshop_server.model.request.PageRequest
import com.angoga.kfd_workshop_server.model.request.PublicationRequest
import com.angoga.kfd_workshop_server.model.response.PublicationResponse
import com.angoga.kfd_workshop_server.model.response.common.PageResponse

interface PublicationService {
    fun findEntityById(id: Long): Publication
    fun getPageCreatedOrders(request: PageRequest): PageResponse<PublicationResponse>
    fun getById(id: Long): PublicationResponse
    fun createPublication(request: PublicationRequest): PublicationResponse
    fun likePublication(publicationId: Long, isLiked: Boolean): PublicationResponse
}