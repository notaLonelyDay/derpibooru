package me.lonelyday.api.controller

import kotlinx.coroutines.runBlocking
import me.lonelyday.api.getRestController
import org.junit.Test


class RestControllerTest {

    @Test
    fun getService() {

        val service = getRestController()
        runBlocking {
            service.getService()
        }
    }
}