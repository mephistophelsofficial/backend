package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "`Key`")
class Key(
    @Column(name = "key", nullable = false)
    var key: String,

    @Column(name = "fingerprint", nullable = false)
    var fingerprint : String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
) : AbstractEntity() {
}