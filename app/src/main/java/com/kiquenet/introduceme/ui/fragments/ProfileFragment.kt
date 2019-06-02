package com.kiquenet.introduceme.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.domain.UserInformationUseCase.UserInfoUi
import com.kiquenet.introduceme.view_models.UserInfoViewModel
import com.kiquenet.introduceme.view_models.factory.ViewModelFactory
import javax.inject.Inject


/**
 * Fragment to show profile of the user.
 * @author n.diazgranados
 */
class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var text: TextView

    companion object {
        fun newInstance() = ProfileFragment()
    }

    //private val userInfoViewModel: UserInfoViewModel by viewModels()
    private lateinit var userInfoViewModel: UserInfoViewModel

    private val userInfoObserver = Observer<UserInfoUi> { newValue ->
        newValue?.let{

            Toast.makeText(appComponent.getAppContext(), "value: ${newValue}", Toast.LENGTH_LONG).show()
            text.setText("value: ${newValue}")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootFrag = inflater.inflate(R.layout.frag_profile, container, false)
        text = rootFrag.findViewById(R.id.frag_profile_text)
        return rootFrag

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserInfoViewModel::class.java)
        userInfoViewModel.userInfo.observe(viewLifecycleOwner, userInfoObserver)
        userInfoViewModel.getUserInfo(9L)
    }
}