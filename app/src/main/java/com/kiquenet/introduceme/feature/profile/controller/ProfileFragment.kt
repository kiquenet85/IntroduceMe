package com.kiquenet.introduceme.feature.profile.controller

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.common.controller.fragments.BaseFragment
import com.kiquenet.introduceme.common.sticky_header.PinnedHeaderItemDecoration
import com.kiquenet.introduceme.common.view_models.factory.ViewModelFactory
import com.kiquenet.introduceme.feature.profile.adapter.ProfileAdapter
import com.kiquenet.introduceme.feature.profile.view_model.UserInfoViewModel
import com.kiquenet.introduceme.util.androidLazy
import kotlinx.android.synthetic.main.frag_profile.*
import javax.inject.Inject

/**
 * Fragment to show profile of the user.
 * @author n.diazgranados
 */
class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val cvAdapter by androidLazy {
        ProfileAdapter(
            viewLifecycleOwner,
            userInfoViewModel.userInfo
        )
    }

    //private val userInfoViewModel: UserInfoViewModel by viewModels()
    private lateinit var userInfoViewModel: UserInfoViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootFrag = inflater.inflate(R.layout.frag_profile, container, false)
        return rootFrag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserInfoViewModel::class.java)

        frag_profile_sections.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            setHasFixedSize(true)
            adapter = cvAdapter
            frag_profile_sections.addItemDecoration(PinnedHeaderItemDecoration())
            setItemViewCacheSize(20)
        }

        //TODO Create login screen and change this burden value.
        userInfoViewModel.getUserInfo(6L)
    }
}
