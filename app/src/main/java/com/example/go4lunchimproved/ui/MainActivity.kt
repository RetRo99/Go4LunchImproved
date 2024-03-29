package com.example.go4lunchimproved.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.adapters.TabAdapter
import com.example.go4lunchimproved.model.User
import com.example.go4lunchimproved.network.FireBaseManager.getCurrentUser
import com.example.go4lunchimproved.network.FireBaseManager.getFireBaseUser
import com.example.go4lunchimproved.network.FireBaseManager.setFireStoreUser
import com.example.go4lunchimproved.utils.loadProfilePhoto
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private val RC_SIGN_IN = 123
    private lateinit var observer: Observer<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startApp()


    }


    private fun setupNavigation() {

        val OFFSCREEN_PAGES = 2
        val adapter: PagerAdapter =
            TabAdapter(supportFragmentManager, this)

        pager.adapter = adapter
        pager.offscreenPageLimit = OFFSCREEN_PAGES


        tab_layout.setupWithViewPager(pager)
        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_map_black_24dp)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_list_black_24dp)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_group_black_24dp)
        tab_layout.tabIndicatorGravity




        drawerLayout = findViewById(R.id.drawer_layout)




        navigationView = findViewById(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener(this)

        setupHeader()


    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    @SuppressLint("StringFormatInvalid")
    fun startApp() {
        observer = Observer { user ->
            val header = navigationView.getHeaderView(0)
            header.apply {
                nav_img_profile.loadProfilePhoto(user.photoUrl.toString())
                nav_email.text = user.email
                nav_name.text = user.name
            }
        }

        if (getFireBaseUser() == null) {


            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build(),
                AuthUI.IdpConfig.TwitterBuilder().build()
            )


            val customLayout = AuthMethodPickerLayout.Builder(R.layout.activity_login)
                .setGoogleButtonId(R.id.signIn_google)
                .setFacebookButtonId(R.id.signIn_facebook)
                .setTwitterButtonId(R.id.signIn_twitter)
                .build()

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers as MutableList<AuthUI.IdpConfig>)
                    .setAuthMethodPickerLayout(customLayout)
                    .setTheme(R.style.AuthenticationTheme)
                    .setIsSmartLockEnabled(false)
                    .build(),
                RC_SIGN_IN
            )

        } else {
            setFireStoreUser()
            setupNavigation()
        }

    }

    private fun setupHeader() {

        getCurrentUser().observe(this, observer)

    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START) else super.onBackPressed()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                setFireStoreUser()
                setupNavigation()

            }

        }

    }

}

