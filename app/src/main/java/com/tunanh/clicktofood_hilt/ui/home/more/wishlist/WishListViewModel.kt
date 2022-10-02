package com.tunanh.clicktofood_hilt.ui.home.more.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : BaseViewModel() {
    var withList = MutableLiveData<List<Food>>()

    init {
        loadWishList()

    }

    private fun loadWishList() {
        viewModelScope.launch {
            val data = localRepository.getWithList()
            withList.postValue(data)
        }
    }

}