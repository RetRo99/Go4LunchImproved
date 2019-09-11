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
import com.example.go4lunchimproved.model.FireStoreRestaurant
import com.example.go4lunchimproved.network.FireBaseManager.saveRestaurants


object Repository {


    private val nearbySquareRestaurants: MutableLiveData<List<FireStoreRestaurant>> = MutableLiveData()

    fun getNearbySquareRestaurant(): MutableLiveData<List<FireStoreRestaurant>> {
        return nearbySquareRestaurants
    }

    private var listRestaurants: MutableLiveData<List<FireStoreRestaurant>> = MutableLiveData()

    fun getListRestaurants(): MutableLiveData<List<FireStoreRestaurant>> {
        return listRestaurants


    }


    fun filterListRestaurants(criteria: String = "") {
        if (criteria.isEmpty()) listRestaurants.postValue(nearbySquareRestaurants.value)
        else {
            val matchingList = ArrayList<FireStoreRestaurant>()
            if (nearbySquareRestaurants.value != null) {
                for (venue in nearbySquareRestaurants.value!!) {
                    if (venue.matchesCriteria(criteria)) matchingList.add(venue)
            }

            }
            listRestaurants.value= matchingList

        }


    }


    fun loadSquareNearbyRestaurants(locationString: String, arrayList: ArrayList<FireStoreRestaurant>?,distance: String = "100") {

        if (arrayList == null) {


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
                    val simpleRestaurantList:ArrayList<FireStoreRestaurant> = ArrayList()
                    for (item in it){
                        val simpleRestaurant = FireStoreRestaurant(item.id, item.name, item.getType(), item.getAddressText(), item.getOpeningHours(), item.getPhotoUrl(), item.getPhoneNumber(), item.getWebsite(), item.getDistanceText(), item.location.lat, item.location.lng)
                        simpleRestaurantList.add(simpleRestaurant)
                    }
                    saveRestaurants(simpleRestaurantList)
                    nearbySquareRestaurants.postValue(simpleRestaurantList); Log.d("onsuccess", "babee")
                    listRestaurants.postValue(simpleRestaurantList)
                },
                onError = { it.printStackTrace();Log.d("onrerror", "babee") }
            )

        }
        else{
            nearbySquareRestaurants.postValue(arrayList); Log.d("onsuccess", "babee")
            listRestaurants.postValue(arrayList)

        }
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