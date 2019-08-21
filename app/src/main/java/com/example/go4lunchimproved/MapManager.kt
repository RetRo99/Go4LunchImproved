package com.example.go4lunchimproved

import android.content.Context
import android.location.Location
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager

class MapManager(private val location: Location, private val context:Context, val mapBoxMap: MapboxMap, val mapView: MapView) {

    private val cameraPositionCreator=  CameraPositionCreator()
    private val markerCreator =MarkerCreator()
   fun animateCamera(){
       mapBoxMap.animateCamera(
           (CameraUpdateFactory.newCameraPosition(cameraPositionCreator.getFromLocation(location))), 5000)

   }

    fun setMarker(){
        val markerManager = MarkerViewManager(mapView,mapBoxMap)
        markerManager.addMarker(markerCreator.getMarker(context,location))
    }
}