package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.model.response.web_authn.KeysResponse

interface KeysService {

    fun getKeys() : KeysResponse

}