package com.tunanh.clicktofood_hilt.ui.home.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.AppPreferences
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
@HiltViewModel
class CartViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    appPreferences: AppPreferences
) :
    BaseViewModel() {

    val cart = MutableLiveData<List<Food>>()
    private val random = UUID.randomUUID().toString()
    private var database1: DatabaseReference = Firebase.database.reference
    private val myReferenceOrder = database1.child("app/user")
        .child(appPreferences.getToken()).child("order")
    private val myReferenceCart = database1.child("app/user")
        .child(appPreferences.getToken()).child("card")
    var loadDone: (() -> Unit)? = null

    init {
        addToCart()
    }

    fun addToCart() {
        viewModelScope.launch {
            val data = localRepository.getCard()
            cart.postValue(data)
        }
    }


    fun deleteCart(id: Long) {
        viewModelScope.launch {
            localRepository.deleteById(id)
        }
    }

    fun placeOrder(array: ArrayList<Food>) {
        val job1 = viewModelScope.launch {
            myReferenceOrder.child(random).setValue(array)
        }
        val job2 = viewModelScope.launch {
            job1.join()
            for (food in array) {
                val data = food.apply { amount = 0 }
                localRepository.updateFood(data)
                myReferenceCart.child(food.id.toString()).setValue(data)
            }
        }
        viewModelScope.launch {
            job2.join()
            loadDone?.invoke()
        }

    }
}