package com.droidcba.kedditbysteps.features.news.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.droidcba.kedditbysteps.commons.adapter.AdapterViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewTypeDelegateAdapter
import com.kiquenet.introduceme.common.adapter.delegate.*
import com.kiquenet.introduceme.common.sticky_header.PinnedHeaderItemDecoration
import com.kiquenet.introduceme.feature.profile.view_model.UserInfoViewModel

/**
 * @author n.diazgranados
 */
class ProfileAdapter
    (lifecycleOwner: LifecycleOwner, val userInfo: LiveData<UserInfoViewModel.UserInfoEvent>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), PinnedHeaderItemDecoration.PinnedHeaderAdapter {

    companion object {
        val TAG = ProfileAdapter::class.java.simpleName
    }

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterViewType.LOADING
    }
    private val errorItem = object : ViewType {
        override fun getViewType() = AdapterViewType.ERROR
    }
    private val headerItem = object : ViewType {
        override fun getViewType() = AdapterViewType.HEADER
    }

    private val userInfoObserver = Observer<UserInfoViewModel.UserInfoEvent> { newValue ->
        newValue?.let {
            Log.d(TAG, "Observing new value $newValue")
            addOrUpdateAdapter(newValue)
        }
    }

    private fun addOrUpdateAdapter(newValue: UserInfoViewModel.UserInfoEvent) {
        when (items.size) {
            0, 1 -> addUserInfo(newValue)
            else -> updateUserInfo(newValue)
        }
    }

    init {
        delegateAdapters.put(loadingItem.getViewType(), LoadingDelegateAdapter())
        delegateAdapters.put(headerItem.getViewType(), HeaderDelegateAdapter())
        delegateAdapters.put(AdapterViewType.WORK_EXPERIENCE, WorkExperienceDelegateAdapter())
        delegateAdapters.put(AdapterViewType.ACADEMIUM, AcademiumDelegateAdapter())
        delegateAdapters.put(AdapterViewType.COURSES, CourseDelegateAdapter())
        items = ArrayList()
        showLoading()

        userInfo.observe(lifecycleOwner, userInfoObserver)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addUserInfo(userInfoEvent: UserInfoViewModel.UserInfoEvent) {
        // first remove loading or error and notify
        if (items.size == 1) {
            items.removeAt(getLastPosition())
            notifyItemRemoved(getLastPosition())
        }

        // insert userInfo sections.
        items.add(userInfoEvent.headers.get(AdapterViewType.WORK_EXPERIENCE) as ViewType)
        items.addAll(userInfoEvent.workExperienceItems?.asIterable() ?: emptyList())

        items.add(userInfoEvent.headers.get(AdapterViewType.ACADEMIUM) as ViewType)
        items.addAll(userInfoEvent.academiumItems?.asIterable() ?: emptyList())

        items.add(userInfoEvent.headers.get(AdapterViewType.COURSES) as ViewType)
        items.addAll(userInfoEvent.courseItems?.asIterable() ?: emptyList())
        notifyItemRangeChanged(0, items.size)
    }

    //TODO is possible to improve distinct filter in live data just changing necessary rows.
    fun updateUserInfo(userInfoEvent: UserInfoViewModel.UserInfoEvent) {
        // Remove all info and fill with new one
        if (!items.isEmpty()) {
            items.clear()
            notifyItemRangeRemoved(0, getLastPosition())
        }

        addUserInfo(userInfoEvent)
    }

    fun showError() {
        if (!items.isEmpty()) {
            items.clear()
            notifyItemRangeRemoved(0, getLastPosition())
        }

        items.add(errorItem)
        notifyItemInserted(getLastPosition())
    }

    fun showLoading() {
        if (!items.isEmpty()) {
            items.clear()
            notifyItemRangeRemoved(0, getLastPosition())
        }

        items.add(loadingItem)
        notifyItemInserted(getLastPosition())
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

    override fun isPinnedViewType(viewType: Int): Boolean {
        return headerItem.getViewType().equals(viewType)
    }
}