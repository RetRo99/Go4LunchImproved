package com.example.go4lunchimproved


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.pierry.simpletoast.SimpleToast
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import mumayank.com.airlocationlibrary.AirLocation


class MapViewFragment : Fragment() {

    private lateinit var mapBoxMap: MapboxMap
    private lateinit var mapView: MapView
    private var airLocation: AirLocation? = null // ADD THIS LINE ON TOP


    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(context!!, BuildConfig.CREDENTIALS_KEY)

        return inflater.inflate(R.layout.fragment_map_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mapView = view.findViewById(R.id.mapView)



        mapView.onCreate(savedInstanceState)


        airLocation = AirLocation(activity!!, true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {

            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                // couldn't fetch location due to reason available in locationFailedEnum
                // you may optionally do something to inform the user, even though the reason may be obvious
            }

        })

        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {


            }

            mapBoxMap  = mapboxMap
            airLocation = AirLocation(activity!!, true, true, object: AirLocation.Callbacks {
                override fun onSuccess(location: Location) {

                    val position = CameraPosition.Builder()
                        .target(LatLng(location.latitude, location.longitude))
                        .zoom(10.0)
                        .tilt(20.0)
                        .build()

                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 5000)

                }

                override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                    // couldn't fetch location due to reason available in locationFailedEnum
                    // you may optionally do something to inform the user, even though the reason may be obvious
                }

            })


        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLocation?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)


    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        airLocation?.onRequestPermissionsResult(requestCode, permissions, grantResults) // ADD THIS LINE INSIDE onRequestPermissionResult
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
