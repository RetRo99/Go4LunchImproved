package com.example.go4lunchimproved

import com.bumptech.glide.load.resource.drawable.ResourceDrawableDecoder
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SquareRestaurantApi {


    @GET("search")
    fun getNearbySquareRestaurants(@Query("ll", encoded = true) location:String, @Query("radius") radius:String = "10"): Flowable<SquareRestaurantResponse>

    @GET("{restaurant_id}/")
    fun getGetSquareRestaurantDetails(@Path("restaurant_id") id:String):Flowable<SquareDetailsResponse>
}