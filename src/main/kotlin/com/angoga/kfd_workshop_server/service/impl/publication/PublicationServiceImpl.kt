package com.angoga.kfd_workshop_server.service.impl.publication

import com.angoga.kfd_workshop_server.database.entity.Like
import com.angoga.kfd_workshop_server.database.entity.Publication
import com.angoga.kfd_workshop_server.database.repository.LikeDao
import com.angoga.kfd_workshop_server.database.repository.PublicationDao
import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.mappers.PublicationMapper
import com.angoga.kfd_workshop_server.model.request.PageRequest
import com.angoga.kfd_workshop_server.model.request.PublicationRequest
import com.angoga.kfd_workshop_server.model.response.PublicationResponse
import com.angoga.kfd_workshop_server.model.response.common.PageResponse
import com.angoga.kfd_workshop_server.service.PublicationService
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.stereotype.Service

@Service
class PublicationServiceImpl(
    private val userService: UserService,
    private val dao: PublicationDao,
    private val likeDao: LikeDao,
    private val mapper: PublicationMapper
): PublicationService {
    override fun findEntityById(id: Long): Publication {
        return dao.findById(id).orElseThrow { ResourceNotFoundException(id) }
    }

    private fun findLikeEntityById(id: Long): Like {
        return likeDao.findById(id).orElseThrow { ResourceNotFoundException(id) }
    }

    override fun getPageCreatedOrders(request: PageRequest): PageResponse<PublicationResponse> {
        return mapper.asPageResponse(dao.findAll(request.pageable))
    }

    override fun getById(id: Long): PublicationResponse {
        return mapper.asResponse(findEntityById(id))
    }

    override fun createPublication(request: PublicationRequest): PublicationResponse {
        val entity = dao.save(mapper.asEntity(request))
        return mapper.asResponse(entity)
    }

    override fun likePublication(publicationId: Long, isLiked: Boolean): PublicationResponse {
        val user = userService.findEntityById(getPrincipal())
        val publication = findEntityById(publicationId)
        if (isLiked) {
            val like = Like(user, publication)
            likeDao.save(like)
        } else {
            val like = likeDao.findByUserAndPublication(user, publication)
            like.ifPresent { likeDao.delete(it) }
        }
        return mapper.asResponse(publication)
    }
}