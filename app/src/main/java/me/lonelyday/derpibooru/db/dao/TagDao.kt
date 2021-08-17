package me.lonelyday.derpibooru.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.lonelyday.derpibooru.db.vo.Tag

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg tags: Tag)

    @Query("SELECT * FROM tags WHERE id=:id")
    suspend fun load(id: Int): Tag?
}