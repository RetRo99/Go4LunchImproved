package com.example.go4lunchimproved


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_map_view.*
import mumayank.com.airlocationlibrary.AirLocation


class MapViewFragment : Fragment() {

    private lateinit var observer: Observer<List<Venue>>
    private lateinit var mapView: MapView
    private var airLocation: AirLocation? = null
    private lateinit var cameraPositionCreator: CameraPositionCreator
    private lateinit var mapManager: MapManager
    private lateinit var mapBoxMap: MapboxMap
    private lateinit var repo: Repository
    private lateinit var quickPermissionsOption: QuickPermissionsOptions


    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(context!!, BuildConfig.CREDENTIALS_KEY)

        return inflater.inflate(R.layout.fragment_map_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = true,
            rationaleMessage = resources.getString(R.string.permissions_denied),
            permanentlyDeniedMessage = "Custom permanently denied message"
        )

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS)
            mapBoxMap = mapboxMap
            getLocationAndZoom()
        }
        repo = Repository

        observer = Observer { venues ->
            for (item in venues) {

                mapManager.setMarker(item.location.lat, item.location.lng, item)

            }
        }

        locationFab.setOnClickListener {
            if (::mapManager.isInitialized) {
                mapManager.animateCamera(1000)
            } else {
                getLocationAndZoom()
            }

        }

        repo.getNearbySquareRestaurant().observe(this, observer)

    }

    private fun getLocationAndZoom() = runWithPermissions(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION, options = quickPermissionsOption
    ) {
        airLocation = AirLocation(activity!!, false, false, object : AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                val lon = location.longitude
                val lat = location.latitude
                val locationString = "$lat,$lon"

                repo.loadSquareNearbyRestaurants(locationString)
                cameraPositionCreator = CameraPositionCreator()
                mapManager = MapManager(location, context!!, mapBoxMap, mapView)
                mapManager.animateCamera()


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
