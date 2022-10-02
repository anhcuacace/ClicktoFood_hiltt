package com.tunanh.clicktofood_hilt.data.local.dao

import androidx.room.*
import com.tunanh.clicktofood_hilt.data.local.model.Food

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(food: Food)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserts(foodList: List<Food>?)

    @Query("select * from Food")
    suspend fun getAllFood(): List<Food>

    @Query("DELETE FROM Food WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("select * FROM Food WHERE id= :id")
    suspend fun selectFromId(id: Long): Food

    @Query("SELECT EXISTS(SELECT * FROM Food WHERE id = :id)")
    suspend fun isRowIsExist(id : Long) : Boolean

    @Query("DELETE FROM Food")
    suspend fun deleteAllFood()

    @Update
    suspend fun update(food: Food)

    @Query("SELECT * FROM Food WHERE amount > 0")
    suspend fun loadCard (): List<Food>

    @Query("SELECT * FROM Food WHERE title LIKE '%' || :name  || '%' ORDER BY id DESC")
    suspend fun findFoodByName(name: String): List<Food>

    @Query("SELECT * FROM Food WHERE `like` =1 ")
    suspend fun getWishList(): List<Food>

}