package com.tunanh.clicktofood_hilt.ui.home.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.AppPreferences
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val localRepository: LocalRepository
) : BaseViewModel() {
init{
    getUser()
}

    var user = MutableLiveData<User>()


    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.deleteUser()
            localRepository.deleteAll()
            appPreferences.setEmail("")
            appPreferences.setToken("")
            localRepository.deleteAllFood()
        }
    }
    fun getUser() {
        viewModelScope.launch {
            val data = localRepository.getUser()
            user.postValue(data)
        }
    }
}