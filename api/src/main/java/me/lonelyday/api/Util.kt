package me.lonelyday.api

import me.lonelyday.api.controller.RestController
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun getOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

fun getRestController(): RestController {
    return RestController(getOkHttpClient(), "https://derpibooru.org/api/v1/json/")
}