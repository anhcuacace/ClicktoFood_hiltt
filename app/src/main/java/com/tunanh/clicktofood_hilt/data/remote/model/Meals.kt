package com.tunanh.clicktofood_hilt.data.remote.model

import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("meals")
    val meals: List<Meal>? = null
)

data class Meal(
    @SerializedName("idMeal")
    val id: Long? = null,
    @SerializedName("strMeal")
    val title: String? = null,
    @SerializedName("strMealThumb")
    val img: String? = null
)
//data class MealsResults(val results: List<Meals>)


data class MealsForId(
    @SerializedName("meals")
    val meals: List<Meal>? = null
)

