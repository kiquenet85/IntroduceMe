package com.kiquenet.introduceme.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.droidcba.kedditbysteps.commons.adapter.AdapterViewType
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.common.adapter.delegate.AcademiumItem
import com.kiquenet.introduceme.common.adapter.delegate.CourseItem
import com.kiquenet.introduceme.common.adapter.delegate.HeaderItem
import com.kiquenet.introduceme.common.adapter.delegate.WorkExperienceItem
import com.kiquenet.introduceme.di.scope.ApplicationContext
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoInteractor
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoUi
import com.kiquenet.introduceme.util.getDistinct
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author n.diazgranados
 */
class UserInfoViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    val userInfoInteractor: UserInfoInteractor
) : ViewModel() {

    var userInfo: LiveData<UserInfoEvent> = userInfoInteractor.userInfo.getDistinct().map { userInfoUi ->
        UserInfoEvent(userInfoUi)
    }

    /**
     * Event for populate user information in Profile screen.
     */
    inner class UserInfoEvent(userInfoUi: UserInfoUi) {
        val headers = mutableMapOf<Int, HeaderItem>()
        val workExperienceItems: List<WorkExperienceItem>?
        val courseItems: List<CourseItem>?
        val academiumItems: List<AcademiumItem>?

        init {
            headers.put(
                AdapterViewType.WORK_EXPERIENCE,
                HeaderItem(appContext.getString(R.string.profile_adapter_header_work_exp))
            )
            workExperienceItems = (userInfoUi.workExperience)?.map { WorkExperienceItem(it) }

            headers.put(
                AdapterViewType.ACADEMIUM,
                HeaderItem(appContext.getString(R.string.profile_adapter_header_academy))
            )
            academiumItems = (userInfoUi.academia)?.map { AcademiumItem(it) }

            headers.put(
                AdapterViewType.COURSES,
                HeaderItem(appContext.getString(R.string.profile_adapter_header_courses))
            )
            courseItems = (userInfoUi.courses)?.map { CourseItem(it) }
        }
    }

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