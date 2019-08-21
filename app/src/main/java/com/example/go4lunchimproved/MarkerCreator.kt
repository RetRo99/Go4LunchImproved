package com.example.go4lunchimproved

import android.content.Context
import android.location.Location
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.pierry.simpletoast.SimpleToast
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager

class MarkerCreator {

    fun getMarker(context:Context, location:Location):MarkerView   {
        val markerIconMaker = MarkerIconMaker()
        return MarkerView(LatLng(location.latitude, location.longitude), markerIconMaker.getIcon(context))
    }

}