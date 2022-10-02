package com.tunanh.clicktofood_hilt.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Food(
    @PrimaryKey
    val id: Long = 0,
    val title: String = "",
    val cost: Int = 0,
    var like:Boolean=false,
    val star: Double? = 0.0,
    val img: String? = "",
    var amount:Int=0
)
