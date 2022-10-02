package com.tunanh.clicktofood_hilt.ui.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunanh.clicktofood_hilt.R
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.AppPreferences
import com.tunanh.clicktofood_hilt.data.local.model.IntroItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class IntroViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : BaseViewModel() {

    private var introItemList = mutableListOf<IntroItem>()
    var introList = MutableLiveData<List<IntroItem>>()

    init {
        addItem()

    }

    fun setIntro() {
        appPreferences.setIntro(true)
    }

    private fun addItem() {
        viewModelScope.launch {
            addToList(
                "ORDER ONLINE",
                "you can eat anything\nas long as Click to Food",
                R.drawable.intro1
            )
            addToList(
                "SELECT FOOD",
                "Just sit at home and Click to Food take care",
                R.drawable.intro2
            )
            addToList(
                "DELIVERY",
                "Just sit at home and Click to Food take care",
                R.drawable.intro3
            )
            introList.value = introItemList
        }
    }

    private fun addToList(title: String, description: String, img: Int) {
        val introItem = IntroItem(title, description, img)
        introItemList.add(introItem)
    }
}