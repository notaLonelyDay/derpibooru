package me.lonelyday.api.controller

import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import me.lonelyday.api.interfaces.DerpibooruApi
import me.lonelyday.api.interfaces.DerpibooruService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class RestController(
    private val httpClient: OkHttpClient,
    private val baseUrl: String
) {

//    fun getService(): DerpibooruService {
//        val moshi = Moshi.Builder()
//            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
//            .build()
//        val moshiFactory = MoshiConverterFactory.create(moshi)
//
//        val derpibooruApi = Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(moshiFactory)
//            .client(httpClient)
//            .build()
//            .create(DerpibooruApi::class.java)
//
//        return DerpibooruServiceImpl(derpibooruApi)
//    }
}
