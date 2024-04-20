package com.angoga.kfd_workshop_server.model.response

import com.angoga.kfd_workshop_server.model.response.common.AbstractResponse
import java.time.LocalDateTime

class PublicationResponse(
    id: Long,
    var title: String,
    var content: String,
    var isLiked: Boolean = false,
    var likesCount: Int = 0
): AbstractResponse(id)