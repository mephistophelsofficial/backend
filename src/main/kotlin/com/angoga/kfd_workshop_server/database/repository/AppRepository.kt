package com.angoga.kfd_workshop_server.database.repository

import com.angoga.kfd_workshop_server.database.entity.AbstractEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface AppRepository<T : AbstractEntity> : CrudRepository<T, Long>