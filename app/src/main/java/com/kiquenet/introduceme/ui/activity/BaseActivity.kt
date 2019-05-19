package com.kiquenet.introduceme.ui.activity

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.ui.IntroduceMeApp
import com.kiquenet.introduceme.ui.di.component.ActivityComponent
import com.kiquenet.introduceme.ui.di.component.ApplicationComponent
import com.kiquenet.introduceme.ui.di.component.DaggerActivityComponent
import com.kiquenet.introduceme.ui.di.module.Activity.ActivityModule
import com.kiquenet.introduceme.ui.network.NetworkManager
import com.kiquenet.introduceme.ui.network.WifiReceiver
import java.io.Serializable
import javax.inject.Inject

/**
 * @author n.diazgranados
 * Common activity functionality.
 */
open class BaseActivity : AppCompatActivity() {

    companion object {
        val WIFI_RECEIVER_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    private var activityComponent: ActivityComponent? = null
    private var applicationComponent: ApplicationComponent? = null
    protected var activeConnection: MenuItem? = null
    lateinit private var currentActivity: Activity
    lateinit private var currentContext: Context
    lateinit protected var wifiReceiver: WifiReceiver

    @Inject
    lateinit protected var networkManager: NetworkManager

    protected fun getLayoutResourceId(): Int = R.layout.base_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent = (application as? IntroduceMeApp)?.appComponent
        applicationComponent?.inject(this)
        getActivityComponent()

        registerNetworkEvents()

        setContentView(getLayoutResourceId())
    }

    protected fun registerNetworkEvents() {
        wifiReceiver = object : WifiReceiver() {

            override fun onConnectionEvent(networkConnectionChange: NetworkConnectionChange) {
                onConnectionUpdate(networkConnectionChange.isOnline)
            }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(wifiReceiver, IntentFilter(WIFI_RECEIVER_ACTION))
    }

    private fun onConnectionUpdate(isOnline: Boolean) {
        activeConnection?.setVisible(!isOnline)
    }

    fun getFragmentFromActivity(fragTag: String): Fragment? {
        val fragment = supportFragmentManager.findFragmentByTag(fragTag)
        return if (fragment != null && fragment.isVisible) {
            fragment
        } else null
    }

    protected fun navigateToFragment(
        containerId: Int, fragment: Fragment, tag: String, addToBackStack: Boolean,
        animation: CustomAnimations?
    ): Int {
        val transaction = supportFragmentManager.beginTransaction()
        if (animation != null) {
            transaction.setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        // use commitAllowingStateLoss per Support Library bug: https://code.google.com/p/android/issues/detail?id=19917
        // (TODO once I get rid of support lib I can remove this)
        return transaction.replace(containerId, fragment, tag).commitAllowingStateLoss()
    }

    fun getActivityComponent(): ActivityComponent? {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(applicationComponent).build()

            currentActivity = activityComponent!!.activity
            currentContext = activityComponent!!.context
        }
        return activityComponent
    }

    public override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(WIFI_RECEIVER_ACTION)
        registerReceiver(wifiReceiver, intentFilter)
    }

    public override fun onPause() {
        super.onPause()
        unregisterReceiver(wifiReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.base_activity, menu)
        activeConnection = menu.findItem(R.id.active_connection)
        onConnectionUpdate(networkManager.isOnline)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.active_connection -> return true
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * The [CustomAnimations] constructor.
     *
     * @param enter    the enter animation.
     * @param exit     the exit animation.
     * @param popEnter the pop backstack enter animation (only used for fragment transactions).
     * @param popExit  the pop backstack exit animation (only used for fragment transactions).
     */
    class CustomAnimations @JvmOverloads constructor(
        val enter: Int, val exit: Int,
        val popEnter: Int = 0, val popExit: Int = 0
    ) :
        Serializable {

        companion object {
            private const val serialVersionUID = 1L
        }
    }
}