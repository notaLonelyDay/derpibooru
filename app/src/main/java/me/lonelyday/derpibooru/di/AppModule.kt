package me.lonelyday.derpibooru.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.room.Room
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
import me.lonelyday.derpibooru.db.DerpibooruDb
import me.lonelyday.derpibooru.repository.Repository
import me.lonelyday.derpibooru.ui.download.DownloadManager
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
    fun provideRepository(
        database: DerpibooruDb,
        service: DerpibooruService,
        @ApplicationContext appContext: Context
    ): Repository {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)
        return Repository(
            database,
            service,
            sharedPreferences
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): DerpibooruDb {
        return Room.databaseBuilder(
            appContext,
            DerpibooruDb::class.java,
            "database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDownloadManager(
        @ApplicationContext appContext: Context
    ): DownloadManager {
        val downloadManager =
            appContext.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as android.app.DownloadManager
        return DownloadManager(downloadManager, appContext)
    }

}