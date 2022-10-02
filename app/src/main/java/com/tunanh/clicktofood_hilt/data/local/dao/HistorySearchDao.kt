package com.tunanh.clicktofood_hilt.data.local.dao

import androidx.room.*
import com.tunanh.clicktofood_hilt.data.local.model.KeyWorkSearch

@Dao
interface HistorySearchDao {

    @Query("DELETE FROM tb_search")
    suspend fun deleteAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(keyword: KeyWorkSearch)

    @Delete
    suspend fun deleteHistory(keyword: KeyWorkSearch?)

    @Query("SELECT * FROM tb_search ORDER BY id DESC LIMIT 5")
    suspend fun getHistoryList(): List<KeyWorkSearch>?

    @Query("SELECT * FROM tb_search WHERE name = :name ")
    suspend fun getHistoryByName(name: String): KeyWorkSearch?
}