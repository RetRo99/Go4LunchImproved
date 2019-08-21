package com.example.go4lunchimproved


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import mumayank.com.airlocationlibrary.AirLocation


class MapViewFragment : Fragment() {

    private lateinit var mapView: MapView
    private var airLocation: AirLocation? = null
    private lateinit var cameraPositionCreator: CameraPositionCreator
    private lateinit var mapManager: MapManager
    private lateinit var mapBoxMap: MapboxMap


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
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS)
            mapBoxMap = mapboxMap
            getLocationAndZoom()


        }
    }

    private fun getLocationAndZoom() {
        airLocation = AirLocation(activity!!, false, true, object : AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                cameraPositionCreator = CameraPositionCreator()
                mapManager= MapManager(location, context!!, mapBoxMap,mapView)
                mapManager.animateCamera()
                mapManager.setMarker()

            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                // couldn't fetch location due to reason available in locationFailedEnum
                // you may optionally do something to inform the user, even though the reason may be obvious
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLocation?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)


    }


}
