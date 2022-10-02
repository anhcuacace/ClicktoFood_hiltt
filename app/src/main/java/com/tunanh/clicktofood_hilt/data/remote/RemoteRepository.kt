package com.tunanh.clicktofood_hilt.data.remote

import com.tunanh.clicktofood_hilt.data.remote.model.Categories
import com.tunanh.clicktofood_hilt.data.remote.model.Meals
import com.tunanh.clicktofood.data.remote.model.Slider
import com.tunanh.clicktofood_hilt.data.remote.service.Service
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val service: Service
) {

    suspend fun getAllPhotos(): List<Slider> {
        return service.getAllPhoto("https://click-to-food-3639d-default-rtdb.firebaseio.com/app/slider.json")
    }

    suspend fun getAllCategory(): Categories {
        return service.getAllCategory()
    }

    suspend fun getAllPhoToList(c: String): Meals {
        return service.getAllFoodList(c)
    }

//    suspend fun getFood(id: Long): MealsForId {
//        return service.getFood(id)
//    }
//
//    suspend fun getIdFood(token:String):List<Long>{
//        return service.getAllId("https://click-to-food-3639d-default-rtdb.firebaseio.com/app/user/$token/card.json")
//    }
}
