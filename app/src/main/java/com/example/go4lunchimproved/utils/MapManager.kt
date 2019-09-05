package com.example.go4lunchimproved.utils

import android.content.Context
import android.location.Location
import com.example.go4lunchimproved.model.Venue
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager

class MapManager(
    val location: Location,
    private val context: Context,
    val mapBoxMap: MapboxMap,
    val mapView: MapView
) {

    private val cameraPositionCreator = CameraPositionCreator()
    private val markerCreator = MarkerCreator()
    fun animateCamera(duration: Int = 5000) {
        mapBoxMap.animateCamera(
            (CameraUpdateFactory.newCameraPosition(cameraPositionCreator.getFromLocation(location))),
            duration
        )

    }

    fun setMarker(lat: Double, lng: Double, venue: Venue) {
        val markerManager = MarkerViewManager(mapView, mapBoxMap)
        markerManager.addMarker(markerCreator.getMarker(context, lat, lng, venue))
    }
}