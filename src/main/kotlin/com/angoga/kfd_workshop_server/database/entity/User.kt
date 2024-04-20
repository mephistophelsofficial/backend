package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.*


@Entity
@Table(name = "`User`")
class User(
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "name", nullable = false)
    var name: String,

) : AbstractEntity() {
    @Column(name = "hash", nullable = false)
    var hash: String? = null

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    var key: Key? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var sessions: Set<Session>? = null


}