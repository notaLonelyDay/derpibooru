package me.lonelyday.derpibooru.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.lonelyday.derpibooru.db.dao.ImageDao
import me.lonelyday.derpibooru.db.dao.TagDao
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.db.vo.Tag
import okhttp3.MediaType.Companion.toMediaType
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.ArrayList


@Database(
    entities = [Image::class, Tag::class],
    version = 33
)
@TypeConverters(Converters::class)
abstract class DerpibooruDb : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun tagDao(): TagDao
}

object Converters {
    @TypeConverter
    fun stringFromString(value: String): List<String> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun stringArrayList(list: List<String>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(list)
    }


    @TypeConverter
    fun intFromString(value: String): List<Int> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
        val adapter = moshi.adapter<List<Int>>(type)
        return adapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun intFromList(list: List<Int>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
        val adapter = moshi.adapter<List<Int>>(type)
        return adapter.toJson(list)
    }


    @TypeConverter
    fun mapFromString(value: String): Map<String, String> {
        val moshi = Moshi.Builder().build()
        val type =
            Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val adapter = moshi.adapter<Map<String, String>>(type)
        return adapter.fromJson(value) ?: emptyMap()
    }

    @TypeConverter
    fun fromMap(list: Map<String, String>): String {
        val moshi = Moshi.Builder().build()
        val type =
        Types.newParameterizedType(kotlin.collections.Map::class.java, kotlin.String::class.java, kotlin.String::class.java)
        val adapter = moshi.adapter<kotlin.collections.Map<kotlin.String, kotlin.String>>(type)
        return adapter.toJson(list)
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): Long {
        return value.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDateTime(value: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
    }
}