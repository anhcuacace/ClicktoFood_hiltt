package com.tunanh.clicktofood_hilt.data.remote

import com.tunanh.clicktofood_hilt.data.remote.service.Service
import com.tunanh.clicktofood_hilt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val service: Service,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAllPhotos() = withContext(dispatcher) {
        service.getAllPhoto("https://click-to-food-3639d-default-rtdb.firebaseio.com/app/slider.json")
    }

    suspend fun getAllCategory() = withContext(dispatcher) {
        service.getAllCategory()
    }

    suspend fun getAllPhoToList(c: String) = withContext(dispatcher) {
        service.getAllFoodList(c)
    }

//    suspend fun getFood(id: Long): MealsForId {
//        return service.getFood(id)
//    }
//
//    suspend fun getIdFood(token:String):List<Long>{
//        return service.getAllId("https://click-to-food-3639d-default-rtdb.firebaseio.com/app/user/$token/card.json")
//    }
}
