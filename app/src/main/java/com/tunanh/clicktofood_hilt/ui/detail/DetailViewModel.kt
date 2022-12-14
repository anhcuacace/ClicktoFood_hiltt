package com.tunanh.clicktofood_hilt.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
   private val localRepository: LocalRepository
) : BaseViewModel() {
    var foodList = MutableLiveData<List<Food>>()
    var foodInfo=MutableLiveData<Food>()
init {
    loadFood()
}

    private fun loadFood() {
        viewModelScope.launch {
            foodList.value=localRepository.getAllFood()
        }
    }
    fun loadInfo(id:Long){
        viewModelScope.launch {
            val data= async { localRepository.findFoodById(id) }
            foodInfo.postValue(data.await())
        }
    }



}