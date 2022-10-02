package com.tunanh.clicktofood_hilt.data.local.dao

import androidx.room.*
import com.tunanh.clicktofood_hilt.data.local.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteUser()

    @Update
    suspend fun update(user: User)

    @Query("select * from User")
    suspend fun getUser(): User

    @Query("SELECT EXISTS(SELECT * FROM User WHERE email = :email)")
    suspend fun isRowIsExist(email: String ) : Boolean
}