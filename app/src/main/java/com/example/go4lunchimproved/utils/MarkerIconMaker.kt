package com.example.go4lunchimproved.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.go4lunchimproved.network.FireBaseManager
import com.example.go4lunchimproved.R
import com.example.go4lunchimproved.model.FireStoreRestaurant
import com.example.go4lunchimproved.model.Venue
import com.example.go4lunchimproved.ui.RestaurantDetail


class MarkerIconMaker {

    fun getIcon(context: Context, venue: FireStoreRestaurant): ImageView {
        val imageView = ImageView(context)
        if(FireBaseManager.visitedRestaurants.contains(venue.restaurantID)) imageView.setImageResource(R.drawable.ic_restaurant_marker_green) else imageView.setImageResource(
            R.drawable.ic_restaurant_marker_orange
        )

        val params = LinearLayout.LayoutParams(80, 80)
        return imageView.apply {
            layoutParams = params
            setOnClickListener {
                val detailIntent = Intent(context, RestaurantDetail::class.java)
                detailIntent.putExtra("venue", venue)
                context.startActivity(detailIntent)
            }
        }
    }
}