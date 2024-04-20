package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.*


@Entity
@Table(name = "`Like`")
class Like(
    @OneToOne
    @JoinColumn(name = "userId")
    var user: User,

    @OneToOne
    @JoinColumn(name = "publicationId")
    var publication: Publication
) : AbstractEntity()