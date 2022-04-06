package me.lonelyday.derpibooru.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import me.lonelyday.api.controller.RestController
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test

public fun getRestController(): RestController {
    return RestController(getOkHttpClient(), "https://derpibooru.org/api/v1/json/")
}

fun getOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

class DerpibooruDbTest {


    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val db = Room.databaseBuilder(
        appContext,
        DerpibooruDb::class.java, "database-name"
    ).build()

    private val service = getRestController().getService()

    @Test
    fun mainDao() =
        runBlocking {
            val mainDao = db.imageDao()

//            val images = service.searchImages("safe", 0, 20)
        }


}