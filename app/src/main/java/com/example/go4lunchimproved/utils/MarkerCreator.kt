package com.example.go4lunchimproved.utils

import android.content.Context
import com.example.go4lunchimproved.model.FireStoreRestaurant
import com.example.go4lunchimproved.model.Venue
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView

class MarkerCreator {

    fun getMarker(context: Context, lat: Double, lng: Double, venue: FireStoreRestaurant): MarkerView {
        val markerIconMaker = MarkerIconMaker()
        return MarkerView(LatLng(lat, lng), markerIconMaker.getIcon(context, venue))
    }

}