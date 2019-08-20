package com.example.go4lunchimproved

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {


    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()



    }

    private fun setupNavigation() {

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



    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START) else  super.onBackPressed()

    }






}

