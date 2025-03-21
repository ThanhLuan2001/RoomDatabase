package com.example.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.example.roomdatabase.dao.UserDao
import com.example.roomdatabase.model.User
import javax.inject.Inject

// UserRepository.kt
class UserRepository @Inject constructor(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }
}