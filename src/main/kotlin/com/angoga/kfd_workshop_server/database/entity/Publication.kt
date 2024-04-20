package com.angoga.kfd_workshop_server.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "`Publication`")
class Publication(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String
) : AbstractEntity() {
    @OneToOne
    @JoinColumn(name = "userId")
    lateinit var user: User
}