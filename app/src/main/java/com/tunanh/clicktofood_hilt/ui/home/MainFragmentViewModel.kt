package com.tunanh.clicktofood_hilt.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.remote.RemoteRepository
import com.tunanh.clicktofood_hilt.data.remote.model.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {
    var categoryList = MutableLiveData<Categories>()

    init {
        loadCategory()
    }

    private fun loadCategory() {
        viewModelScope.launch {
            try {
                val data = async { remoteRepository.getAllCategory() }
                categoryList.postValue(data.await())
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }
}