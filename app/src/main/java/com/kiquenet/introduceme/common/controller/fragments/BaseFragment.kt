package com.kiquenet.introduceme.common.controller.fragments

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.kiquenet.introduceme.IntroduceMeApp
import com.kiquenet.introduceme.common.controller.activity.BaseActivity
import com.kiquenet.introduceme.data.db.AppDatabase
import com.kiquenet.introduceme.di.component.ActivityComponent
import com.kiquenet.introduceme.di.component.ApplicationComponent
import javax.inject.Inject

/**
 * @author n.diazgranados
 */
open class BaseFragment : Fragment() {

    @Inject
    lateinit protected var appDatabase: AppDatabase

    lateinit protected var activityComponent: ActivityComponent
    lateinit protected var appComponent: ApplicationComponent

    override fun onAttach(context: Context) {
        appComponent = (context.applicationContext as IntroduceMeApp).appComponent
        appDatabase = appComponent.getDB()
        super.onAttach(context)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        activityComponent = (requireActivity() as BaseActivity).getActivityComponent()!!
        appComponent = (requireActivity().application as IntroduceMeApp).appComponent
        appComponent.inject(this)
        super.onActivityCreated(savedInstanceState)
    }
}
