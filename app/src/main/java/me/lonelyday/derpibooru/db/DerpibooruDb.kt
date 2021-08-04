package me.lonelyday.derpibooru.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.lonelyday.derpibooru.db.dao.MainDao
import me.lonelyday.derpibooru.db.vo.Image


@Database(
    entities = [Image::class],
    version = 1
)
abstract class DerpibooruDb: RoomDatabase() {
    abstract fun mainDao(): MainDao
}
