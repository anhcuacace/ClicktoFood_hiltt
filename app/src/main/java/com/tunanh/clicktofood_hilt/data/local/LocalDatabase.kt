package com.tunanh.clicktofood_hilt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tunanh.clicktofood_hilt.data.local.dao.FoodDao
import com.tunanh.clicktofood_hilt.data.local.dao.HistorySearchDao
import com.tunanh.clicktofood_hilt.data.local.dao.UserDao
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.data.local.model.KeyWorkSearch
import com.tunanh.clicktofood_hilt.data.local.model.User

@Database(
    entities = [ User::class, Food::class, KeyWorkSearch::class],
    version = 12,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao
    abstract fun historySearchDao(): HistorySearchDao
}