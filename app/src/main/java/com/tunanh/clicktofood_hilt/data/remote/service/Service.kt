package com.tunanh.clicktofood_hilt.data.remote.service

import com.tunanh.clicktofood_hilt.data.remote.model.Categories
import com.tunanh.clicktofood_hilt.data.remote.model.Meals
import com.tunanh.clicktofood_hilt.data.remote.model.MealsForId
import com.tunanh.clicktofood.data.remote.model.Slider
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Service {

    @GET
    suspend fun getAllPhoto(@Url url: String): List<Slider>

    @GET("categories.php")
    suspend fun getAllCategory(): Categories

    @GET("filter.php")
    suspend fun getAllFoodList(@Query("c") meals: String): Meals

    @GET("lookup.php")
    suspend fun getFood(@Query("i") id: Long): MealsForId

    @GET
    suspend fun getAllId(@Url url: String):List<Long>
}