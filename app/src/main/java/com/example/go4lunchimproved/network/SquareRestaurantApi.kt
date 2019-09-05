package com.example.go4lunchimproved.network

import com.example.go4lunchimproved.model.SquareDetailsResponse
import com.example.go4lunchimproved.model.SquareRestaurantResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SquareRestaurantApi {


    @GET("search")
    fun getNearbySquareRestaurants(
        @Query(
            "ll",
            encoded = true
        ) location: String, @Query("radius") radius: String = "100"
    ): Flowable<SquareRestaurantResponse>

    @GET("{restaurant_id}/")
    fun getGetSquareRestaurantDetails(@Path("restaurant_id") id: String): Flowable<SquareDetailsResponse>
}