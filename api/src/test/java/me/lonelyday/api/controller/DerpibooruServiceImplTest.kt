package me.lonelyday.api.controller

import kotlinx.coroutines.runBlocking
import me.lonelyday.api.getRestController
import org.junit.Test

class DerpibooruServiceImplTest {

    private val service = getRestController().getService() as DerpibooruServiceImpl

    @Test
    fun searchImages() {
        runBlocking {
            service.searchImages("safe", 0, 50)
            service.searchImages("hand", 10000, 50)
        }
    }

}