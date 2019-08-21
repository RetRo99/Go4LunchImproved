package com.example.go4lunchimproved

import android.content.Context
import android.media.Image
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.pierry.simpletoast.SimpleToast

class MarkerIconMaker {

    fun getIcon(context: Context):ImageView{
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.ic_restaurant_marker_orange)
        val params = LinearLayout.LayoutParams(100, 100)
        return imageView.apply {
            layoutParams = params
           setOnClickListener { SimpleToast.ok(context,"delaa") } }
    }
}