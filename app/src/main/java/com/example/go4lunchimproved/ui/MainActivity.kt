package com.example.go4lunchimproved.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
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


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)


        drawerLayout = findViewById(R.id.drawer_layout)



        navigationView = findViewById(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener(this)

        setupHeader()


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
            val TAG = "Čič"
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                    Log.d(TAG, token)
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                })
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.searchButton -> {



                val fragment = AutocompleteFragment()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.toolbar,fragment,"gege")
                fragmentTransaction.commit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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

