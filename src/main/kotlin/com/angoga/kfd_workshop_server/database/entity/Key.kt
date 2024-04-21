package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "`Key`")
class Key(

    @Column(name = "public_key", nullable = false, length = 5000)
    var publicKey: String,

    @Column(name = "private_key", nullable = false, length = 5000)
    var privateKey: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
) : AbstractEntity() {
}