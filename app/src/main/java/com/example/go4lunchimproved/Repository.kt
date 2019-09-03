package com.example.go4lunchimproved

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


object Repository {

    private var myCompositeDisposable: CompositeDisposable? = null

    private val nearbySquareRestaurants: MutableLiveData<List<Venue>> = MutableLiveData()

    fun getNearbySquareRestaurant(): MutableLiveData<List<Venue>> {
        return nearbySquareRestaurants
    }


    fun loadSquareNearbyRestaurants(locationString: String, distance: String = "100") {

        val observable =
            ApiClient.getClientSquareRestaurant.getNearbySquareRestaurants(locationString, distance)

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

                onSuccess = { nearbySquareRestaurants.postValue(it); Log.d("onsuccess", "babee") },
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