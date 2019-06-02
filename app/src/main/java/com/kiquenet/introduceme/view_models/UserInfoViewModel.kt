package com.kiquenet.introduceme.view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoInteractor
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author n.diazgranados
 */
class UserInfoViewModel @Inject constructor(
    val userInfoInteractor: UserInfoInteractor
) : ViewModel() {

    var userInfo : MediatorLiveData<UserInfoUi> = userInfoInteractor.userInfo

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun getUserInfo(id: Long) = viewModelScope.launch {
        userInfoInteractor.getUserInfo(id)
    }
}