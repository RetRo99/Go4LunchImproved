package com.example.go4lunchimproved

import android.content.Context
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView

class MarkerCreator {

    fun getMarker(context: Context, lat: Double, lng: Double, venue: Venue): MarkerView {
        val markerIconMaker = MarkerIconMaker()
        return MarkerView(LatLng(lat, lng), markerIconMaker.getIcon(context, venue))
    }

}