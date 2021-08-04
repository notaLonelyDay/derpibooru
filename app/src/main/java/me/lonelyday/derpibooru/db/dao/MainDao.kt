package me.lonelyday.derpibooru.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import me.lonelyday.derpibooru.db.vo.Image

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg images: Image)
}