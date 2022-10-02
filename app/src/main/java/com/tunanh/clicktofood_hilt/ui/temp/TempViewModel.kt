package com.tunanh.clicktofood_hilt.ui.temp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.data.remote.RemoteRepository
import com.tunanh.clicktofood_hilt.data.remote.model.Meals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : BaseViewModel() {
    var foodList = MutableLiveData<Meals>()
    fun callApi(it: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = remoteRepository.getAllPhoToList(it)
                foodList.postValue(data)

            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    fun addFoodToDataBase(listFood: List<Food>) {
        viewModelScope.launch {
            for (food in listFood) {
                localRepository.insertOrUpdate(food)
            }
        }
    }
}