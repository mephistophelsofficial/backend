package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table


@Entity
@Table(name = "`Commentary`")
class Commentary(
    @Column(name = "content", nullable = false)
    var content: String
) : AbstractEntity() {
    @OneToOne
    @JoinColumn(name = "userId")
    lateinit var user: User

    @OneToOne
    @JoinColumn(name = "publicationId")
    lateinit var publication: Publication
}