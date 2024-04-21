package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.model.response.KeysResponse

interface KeysService {

    fun getKeys() : KeysResponse

}