package com.tunanh.clicktofood_hilt.ui.home.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.tunanh.clicktofood.data.remote.model.Slider
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.data.local.model.KeyWorkSearch
import com.tunanh.clicktofood_hilt.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) :
    BaseViewModel() {
    var sliderList = MutableLiveData<List<Slider>>()
    var foodList = MutableLiveData<List<Food>>()

    init {
        loadSlider()

        loadRecyclerView()

    }



    private fun loadRecyclerView() {
        var history:List<KeyWorkSearch>?=null
        val job1=viewModelScope.launch {
            history= localRepository.getHistoryList()
        }
        viewModelScope.launch {
            job1.join()
            if (history?.size != 0 ){
                val name=history?.get(history?.size?.minus(1)?:-1)
                val data= name?.let { localRepository.findFoodByName(name.name) }
                foodList.postValue(data!!)
            }
        }
    }



    private fun loadSlider() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = remoteRepository.getAllPhotos()
                sliderList.postValue(data)
            } catch (e: ApiException) {
                e.printStackTrace()
            }

        }
    }

}