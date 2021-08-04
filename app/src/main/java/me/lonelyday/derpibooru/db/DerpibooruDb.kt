package me.lonelyday.derpibooru.db

import androidx.room.Database
import me.lonelyday.derpibooru.db.dao.ImageDao
import me.lonelyday.derpibooru.db.vo.Image


@Database(
    entities = [Image::class],
    version = 1
)
abstract class DerpibooruDb() {
    abstract fun imageDao(): ImageDao
}
