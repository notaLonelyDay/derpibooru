package me.lonelyday.derpibooru.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import me.lonelyday.derpibooru.db.vo.Image

interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg images: Image)
}