package com.tunanh.clicktofood_hilt.data.remote.model

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("categories")
    val Categories: List<Category>? = null
)

data class Category(
    @SerializedName("idCategory")
    val id: Long? = null,
    @SerializedName("strCategory")
    val title: String? = null,
    @SerializedName("strCategoryThumb")
    val image: String? = null,
    @SerializedName("strCategoryDescription")
    val description: String? = null
)