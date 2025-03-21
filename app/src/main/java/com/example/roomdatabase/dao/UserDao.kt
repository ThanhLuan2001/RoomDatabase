package com.example.roomdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roomdatabase.model.User

// UserDao.kt
@Dao
interface UserDao {
    @Query("SELECT * FROM users_table")
    fun getAllUsers(): LiveData<List<User>>

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}