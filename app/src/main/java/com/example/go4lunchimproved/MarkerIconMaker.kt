package com.example.go4lunchimproved

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent



class MarkerIconMaker {

    fun getIcon(context: Context, venue: Venue):ImageView{
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.ic_restaurant_marker_orange)
        val params = LinearLayout.LayoutParams(80, 80)
        return imageView.apply {
            layoutParams = params
           setOnClickListener {
               val detailIntent = Intent(context, RestaurantDetail::class.java)
               detailIntent.putExtra("venue", venue)
               context.startActivity(detailIntent)
           } }
    }
}