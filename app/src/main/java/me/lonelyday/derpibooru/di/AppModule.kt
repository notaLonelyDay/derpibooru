package me.lonelyday.derpibooru.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.lonelyday.api.controller.DerpibooruServiceImpl
import me.lonelyday.api.interfaces.DerpibooruApi
import me.lonelyday.api.interfaces.DerpibooruService
import me.lonelyday.derpibooru.APP_PREFERENCES
import me.lonelyday.derpibooru.BASE_URL
import me.lonelyday.derpibooru.repository.ImagesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
    }

    @Singleton
    @Provides
    fun provideDerpibooruService(
        httpClient: OkHttpClient,
        moshi: Moshi
    ): DerpibooruService {
        val moshiFactory = MoshiConverterFactory.create(moshi)

        val derpibooruApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshiFactory)
            .client(httpClient)
            .build()
            .create(DerpibooruApi::class.java)

        return DerpibooruServiceImpl(derpibooruApi)
    }

    @Singleton
    @Provides
    fun provideImagesRepository(
        service: DerpibooruService,
        @ApplicationContext appContext: Context
    ): ImagesRepository {
        val sharedPreferences =
            appContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        return ImagesRepository(
            service,
            sharedPreferences
        )
    }


}