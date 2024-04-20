package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.*


@Entity
@Table(name = "`Session`")
class Session(
    @OneToOne
    @Column(name = "fingerprint")
    var key: Key,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "challenge")
    var challenge : String,

    @Column(name = "state")
    var state : State

    ) : AbstractEntity() {
}
enum class State{
    OPEN, CLOSED, EXPIRED
}