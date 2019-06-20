package com.kiquenet.introduceme.common.controller.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.feature.profile.controller.ProfileFragment
import com.squareup.picasso.Picasso

/**
 * @author n.diazgranados
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val USER_PICTURE_URL = "https://media.licdn.com/dms/image/C5603AQGyGGCTDrBosw/profile-displayphoto-shrink_200_200/0?e=1564012800&v=beta&t=V4ktpkbMxo7xd13Bw5E1TfC3-tXMv6F42nFaY23xXzM"
    }

    private lateinit var collapsingToolbar: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDrawerandToolbar()

        if (savedInstanceState == null) {
            var arguments: Bundle? = intent.extras

            val profileFragment = ProfileFragment.newInstance()
            if (arguments != null) {
                profileFragment.setArguments(arguments)
            }
            navigateToFragment(profileFragment, addToBackStack = true)
        }
    }

    private fun setUpDrawerandToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        //Collapsing tool bar
        collapsingToolbar = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = "Nestor Enrique Diazgranados Pabon"

        //TODO Replace burned data
        val header = findViewById(R.id.header) as ImageView
        Picasso.get().load(USER_PICTURE_URL).into(header)

        collapsingToolbar.setContentScrimColor(resources.getColor(R.color.colorPrimary))
        collapsingToolbar.setStatusBarScrimColor(resources.getColor(R.color.colorAccent))
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
