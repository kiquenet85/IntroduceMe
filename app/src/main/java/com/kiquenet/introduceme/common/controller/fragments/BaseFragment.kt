package com.kiquenet.introduceme.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.kiquenet.introduceme.IntroduceMeApp
import com.kiquenet.introduceme.data.db.AppDatabase
import com.kiquenet.introduceme.di.component.ActivityComponent
import com.kiquenet.introduceme.di.component.ApplicationComponent
import com.kiquenet.introduceme.ui.activity.BaseActivity
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
        activity?.let {
            activityComponent = (it as BaseActivity).getActivityComponent()!!
        }
        appComponent = (activity?.application as IntroduceMeApp).appComponent
        appComponent.inject(this)
        super.onActivityCreated(savedInstanceState)
    }
}
