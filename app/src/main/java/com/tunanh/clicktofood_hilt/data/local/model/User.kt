package com.tunanh.clicktofood_hilt.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val email: String = "",
    val name: String = "",
    val age: Int = 0,
    val image: String = "",
    val phone: String = "Phone"
)
