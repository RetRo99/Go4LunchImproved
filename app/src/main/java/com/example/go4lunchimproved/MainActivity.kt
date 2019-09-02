package com.example.go4lunchimproved

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.PagerAdapter
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var quickPermissionsOption: QuickPermissionsOptions
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = true,
            rationaleMessage = resources.getString(R.string.permissions_denied),
            permanentlyDeniedMessage = "Custom permanently denied message"
        )

        startApp()


    }


    private fun setupNavigation() = runWithPermissions(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION, options = quickPermissionsOption
    ) {

        val OFFSCREEN_PAGES = 2
        val adapter: PagerAdapter = TabAdapter(supportFragmentManager, this)

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

    fun startApp(){

        if (FirebaseAuth.getInstance().currentUser == null) {
            val RC_SIGN_IN = 123

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

        setupNavigation()

        }
        else{
            setupNavigation()
        }
        
    }

    private fun setupHeader() {
        val header = navigationView.getHeaderView(0)
        val user= FirebaseAuth.getInstance().currentUser

        header.apply {
            nav_img_profile.loadProfilePhoto(user?.photoUrl)
            nav_email.text = user?.email
            nav_name.text = user?.displayName
        }
    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START) else super.onBackPressed()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}

