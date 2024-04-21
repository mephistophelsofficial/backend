package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.model.response.web_authn.ChallengeResponse

interface SessionService {

    fun getSessionChallenge(sessionId: Long) : ChallengeResponse

}