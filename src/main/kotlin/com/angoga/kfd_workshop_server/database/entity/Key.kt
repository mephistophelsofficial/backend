package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.*
import java.security.PrivateKey

@Entity
@Table(name = "`Key`")
class Key(

    @Column(name = "public_key", nullable = false, length = 5000)
    var publicKey: String,

    @Column(name = "private_key", nullable = false, length = 5000)
    var privateKey: String,

    @Deprecated("Misconception idea")
    @Column(name = "fingerprint")
    var fingerprint : String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
) : AbstractEntity() {
}