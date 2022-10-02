package com.tunanh.clicktofood_hilt.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.Food
import com.tunanh.clicktofood_hilt.data.local.model.KeyWorkSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val localRepository: LocalRepository) :
    BaseViewModel() {

    var foodList = MutableLiveData<List<Food>>()
    var historyList = MutableLiveData<List<KeyWorkSearch>>()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            historyList.value = localRepository.getHistoryList()
        }
    }

    fun removeHistory(data: KeyWorkSearch) {
        viewModelScope.launch {
            localRepository.deleteHistory(data)
            historyList.value = localRepository.getHistoryList()
        }

    }

    fun searchData(name: String) {
        viewModelScope.launch {
            val data = localRepository.findFoodByName(name)
            foodList.postValue(data)
        }


    }

    fun addHistory(text: String) {
        viewModelScope.launch {
            if (text.isNotEmpty()) {
                val model = localRepository.getHistoryByName(text)
                if (model != null) {
                    localRepository.deleteHistory(model)
                }
                val history = KeyWorkSearch().apply {
                    this.name = text
                }
                localRepository.insertHistory(history)
                historyList.value = localRepository.getHistoryList()
            }
        }

    }

    fun removeAllHistory() {
        viewModelScope.launch {
            localRepository.deleteAll()
            historyList.value = ArrayList()
        }
    }
}