package com.tunanh.clicktofood_hilt.data.remote.model

import com.google.gson.annotations.SerializedName

data class Slider(
    @SerializedName("id")
    val id: Long ?=null,
    @SerializedName("img")
    val img: String ?=null
)