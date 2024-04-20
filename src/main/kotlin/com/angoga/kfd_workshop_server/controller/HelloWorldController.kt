package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.util.API_PUBLIC
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API_PUBLIC)
class HelloWorldController {

    @GetMapping("/hello")
    fun getHelloWorld(): String {
        return "Hello, world!"
    }
}