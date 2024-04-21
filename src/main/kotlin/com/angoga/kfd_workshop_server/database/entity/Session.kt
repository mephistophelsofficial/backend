package com.angoga.kfd_workshop_server.database.entity

import com.angoga.kfd_workshop_server.util.enums.SessionState
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "`Session`")
class Session(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "challenge")
    var challenge : String,

    @Column(name = "state")
    var sessionState : SessionState,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    ) : AbstractEntity() {
}
