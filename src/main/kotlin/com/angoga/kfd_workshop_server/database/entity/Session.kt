package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "`Session`")
class Session(
    @Deprecated("Misconception idea")
    @Column(name = "fingerprint")
    var fingerprint: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "challenge")
    var challenge : String,

    @Column(name = "state")
    var state : State,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now()

    ) : AbstractEntity() {
}
enum class State{
    OPEN, CLOSED, EXPIRED
}