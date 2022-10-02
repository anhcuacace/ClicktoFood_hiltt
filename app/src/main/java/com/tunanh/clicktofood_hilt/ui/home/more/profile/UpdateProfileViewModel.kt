package com.tunanh.clicktofood_hilt.ui.home.more.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunanh.clicktofood_hilt.base.BaseViewModel
import com.tunanh.clicktofood_hilt.data.local.LocalRepository
import com.tunanh.clicktofood_hilt.data.local.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : BaseViewModel() {
    var user = MutableLiveData<User>()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            user.value = localRepository.getUser()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            localRepository.updateUser(user)

        }
    }
}

