package com.angoga.kfd_workshop_server.model.response.common

import org.springframework.data.domain.Page

class PageResponse<T>(
    val content: List<T>,
    number: Long,
    numberOfElements: Long,
    val totalPages: Long,
) {
    companion object {
        fun <T, R> build(page: Page<T>, asResponse: (element: T) -> R) =
            PageResponse(
                content = page.content.map { asResponse(it) },
                number = page.number.toLong(),
                numberOfElements = page.numberOfElements.toLong(),
                totalPages = page.totalPages.toLong(),
            )
    }

    val page = number + 1
    val size = numberOfElements
}