package com.angoga.kfd_workshop_server.util

fun String.containsAnyPath(vararg paths: String): Boolean {
    return paths.any { Regex("$it(\\W|\$)").containsMatchIn(this) }
}