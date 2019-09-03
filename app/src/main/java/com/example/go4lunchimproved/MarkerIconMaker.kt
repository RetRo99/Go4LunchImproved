package com.example.go4lunchimproved

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout


class MarkerIconMaker {

    fun getIcon(context: Context, venue: Venue): ImageView {
        val imageView = ImageView(context)
        if(FireBaseManager.visitedRestaurants.contains(venue.id)) imageView.setImageResource(R.drawable.ic_restaurant_marker_green) else imageView.setImageResource(R.drawable.ic_restaurant_marker_orange)

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