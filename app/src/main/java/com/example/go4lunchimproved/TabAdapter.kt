package com.example.go4lunchimproved

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class TabAdapter(fm: FragmentManager, private val context: Context):FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private val TAB_TITLES = arrayOf(
        R.string.map_view,
        R.string.restaurant_view,
        R.string.workmates_view
    )


    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return MapViewFragment()
            1 -> return RestaurantListFragment()
            2 -> return WorkmatesListFragment()

        }
        Log.d("position", position.toString())
        return RestaurantListFragment()
    }




    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
}