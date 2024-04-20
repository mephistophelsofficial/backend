package com.angoga.kfd_workshop_server.database.repository

import com.angoga.kfd_workshop_server.database.entity.Key
import com.angoga.kfd_workshop_server.database.entity.User

interface KeyDao : AppRepository<Key> {

    fun findAllByUser(user: User) : List<Key>
}