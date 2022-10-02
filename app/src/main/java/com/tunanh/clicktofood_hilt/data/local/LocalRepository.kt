package com.tunanh.clicktofood_hilt.data.local

import com.tunanh.clicktofood_hilt.data.local.dao.FoodDao
import com.tunanh.clicktofood_hilt.data.local.dao.HistorySearchDao
import com.tunanh.clicktofood_hilt.data.local.dao.UserDao
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.data.local.model.KeyWorkSearch
import com.tunanh.clicktofood_hilt.data.local.model.User
import com.tunanh.clicktofood_hilt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val userDao: UserDao,
    private val foodDao: FoodDao,
    private val historySearchDao: HistorySearchDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {


    //user
    suspend fun insertUser(user: User) = withContext(dispatcher) {
        userDao.addUser(user)
    }

    suspend fun isEmailIsExist(email: String) = withContext(dispatcher) {
        userDao.isRowIsExist(email)
    }

    suspend fun updateUser(user: User) = withContext(dispatcher) {
        userDao.update(user)
    }

    suspend fun deleteUser() = withContext(dispatcher) {
        userDao.deleteUser()
    }

    //food
    suspend fun getUser() = withContext(dispatcher) {
        userDao.getUser()
    }

    suspend fun insertFood(food: Food) = withContext(dispatcher) {
        foodDao.addFood(food)
    }

    suspend fun getAllFood() = withContext(dispatcher) {
        foodDao.getAllFood()
    }

    suspend fun deleteById(id: Long) = withContext(dispatcher) {
        foodDao.deleteById(id)
    }

    suspend fun deleteAllFood() = withContext(dispatcher) {
        foodDao.deleteAllFood()
    }

    suspend fun updateFood(food: Food) = withContext(dispatcher) {
        foodDao.update(food)
    }

    suspend fun isRowIsExist(id: Long) =
        withContext(dispatcher) {
            foodDao.isRowIsExist(id)
        }

    suspend fun insertOrUpdate(food: Food) = withContext(dispatcher) {
        if (foodDao.isRowIsExist(food.id)) foodDao.update(food)
        else foodDao.addFood(food)
    }

    suspend fun addFood(listFood: List<Food>) = withContext(dispatcher) {
        foodDao.inserts(listFood)
    }

    suspend fun getCard(): List<Food> =
        withContext(dispatcher) {
            foodDao.loadCard()
        }

    suspend fun getWithList(): List<Food> =
        withContext(dispatcher) {
            foodDao.getWishList()
        }

    suspend fun findFoodByName(name: String): List<Food> =
        withContext(dispatcher) {
            foodDao.findFoodByName(name)
        }

    suspend fun insertHistory(keyword: KeyWorkSearch) =
        withContext(dispatcher) {
            historySearchDao.insertHistory(keyword)
        }

    suspend fun deleteHistory(keyword: KeyWorkSearch) = withContext(dispatcher) {
        historySearchDao.deleteHistory(keyword)
    }

    suspend fun getHistoryList(): List<KeyWorkSearch>? =
        withContext(dispatcher) {
            historySearchDao.getHistoryList()
        }

    suspend fun deleteAll() {
        withContext(dispatcher) {
            historySearchDao.getHistoryList()
        }
    }

    suspend fun getHistoryByName(text: String): KeyWorkSearch? =
        withContext(dispatcher) {
            historySearchDao.getHistoryByName(text)
        }


}