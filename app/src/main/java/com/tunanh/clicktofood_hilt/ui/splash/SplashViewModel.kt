package com.tunanh.clicktofood_hilt.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.AppPreferences
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    appPreferences: AppPreferences,
    private val localRepository: LocalRepository
) :
    BaseViewModel() {
    var intro = MutableLiveData<Boolean>()
    var user = MutableLiveData<Boolean>()


    init {
        intro.value = appPreferences.getIntro()
        val email = appPreferences.getEmail()
        viewModelScope.launch {
            user.value = localRepository.isEmailIsExist(email)
        }
    }


}