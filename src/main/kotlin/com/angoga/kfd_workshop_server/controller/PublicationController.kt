package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.model.request.PageRequest
import com.angoga.kfd_workshop_server.model.request.PublicationRequest
import com.angoga.kfd_workshop_server.model.response.PublicationResponse
import com.angoga.kfd_workshop_server.model.response.common.PageResponse
import com.angoga.kfd_workshop_server.service.PublicationService
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_VERSION_1/publication")
class PublicationController(
    private val service: PublicationService
) {

    @GetMapping("/list")
    fun getPublication(request: PageRequest): PageResponse<PublicationResponse> {
        return service.getPageCreatedOrders(request)
    }

    @GetMapping("/{publicationId}")
    fun getPublication(@PathVariable publicationId: Long): PublicationResponse {
        return service.getById(publicationId)
    }

    @PostMapping("/{publicationId}/like")
    fun likePublication(@PathVariable publicationId: Long, @RequestParam isLiked: Boolean): PublicationResponse {
        return service.likePublication(publicationId, isLiked)
    }

    @PostMapping
    fun createPublication(request: PublicationRequest): PublicationResponse {
        return service.createPublication(request)
    }
}