package com.example.submissionone.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.example.submissionone.local.entity.FavEntity

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUsers: FavEntity)

    @Update
    fun update(favoriteUsers: FavEntity)

    @Delete
    fun delete(favoriteUsers: FavEntity)

    @Query("SELECT * from fav ORDER BY username ASC")
    fun getAllFavoriteUser(): LiveData<List<FavEntity>>

    @Query("SELECT * FROM fav WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavEntity>
}
