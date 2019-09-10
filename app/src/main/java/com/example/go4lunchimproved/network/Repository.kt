package com.example.go4lunchimproved.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.go4lunchimproved.model.Venue
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import android.R
import android.content.ActivityNotFoundException
import androidx.core.app.ActivityCompat.startActivityForResult
import android.speech.RecognizerIntent
import android.content.Intent
import android.app.Activity




object Repository {


    private val nearbySquareRestaurants: MutableLiveData<List<Venue>> = MutableLiveData()

    fun getNearbySquareRestaurant(): MutableLiveData<List<Venue>> {
        return nearbySquareRestaurants
    }

    private var listRestaurants: MutableLiveData<List<Venue>> = MutableLiveData()

    fun getListRestaurants(): MutableLiveData<List<Venue>> {
        return listRestaurants


    }


    fun filterListRestaurants(criteria: String = "") {
        if (criteria.isEmpty()) listRestaurants.postValue(nearbySquareRestaurants.value)
        else {
            val matchingList = ArrayList<Venue>()
            if (nearbySquareRestaurants.value != null) {
                for (venue in nearbySquareRestaurants.value!!) {
                    if (venue.matchesCriteria(criteria)) matchingList.add(venue)
            }

            }
            listRestaurants.value= matchingList

        }


    }


    fun loadSquareNearbyRestaurants(locationString: String, distance: String = "100") {

        val observable =
            ApiClient.getClientSquareRestaurant.getNearbySquareRestaurants(
                locationString,
                distance
            )

        observable
            .subscribeOn(Schedulers.io())
            .flatMapIterable { it.response.venues }
            .flatMap {
                loadSquareResturantDetail(
                    it.id,
                    it.location.distance
                )
            } // Calls the method which returns a new Observable<Item>
            .observeOn(Schedulers.io())
            .toSortedList()
            .subscribeBy(

                onSuccess = {
                    nearbySquareRestaurants.postValue(it); Log.d("onsuccess", "babee")
                    listRestaurants.postValue(it)
                },
                onError = { it.printStackTrace();Log.d("onrerror", "babee") }
            )
    }

    private fun loadSquareResturantDetail(id: String, distance: Int?): Flowable<Venue> {
        val observer = ApiClient.getClientSquareRestaurant.getGetSquareRestaurantDetails(id)
        return observer
            .subscribeOn(Schedulers.io())
            .map {
                it.response?.venue?.location?.distance = distance
                it.response?.venue
            }


    }
  

}