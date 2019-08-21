package com.example.go4lunchimproved

import android.location.Location
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng

class CameraPositionCreator {

    fun getFromLocation(location: Location): CameraPosition {
        return CameraPosition.Builder()
            .target(LatLng(location.latitude, location.longitude))
            .zoom(14.0)
            .tilt(20.0)
            .build()


    }
}