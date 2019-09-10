package com.example.go4lunchimproved.network

import com.example.go4lunchimproved.BuildConfig
import com.example.go4lunchimproved.utils.getCurrentDate
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


object ApiClient {

    var BASE_URL_SQUARE_API: String = "https://api.foursquare.com/v2/venues/"


    val getClientSquareRestaurant: SquareRestaurantApi
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            val client =
                OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor { chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url()

                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("client_id",
                            BuildConfig.FOURSQUARE_ID
                        )
                        .addQueryParameter("client_secret",
                            BuildConfig.FOURSQUARE_SECRET
                        )
                        .addQueryParameter("categoryId", "4d4b7105d754a06374d81259")
                        .addQueryParameter("v", getCurrentDate())
                        .build()

                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                        .url(url)


                    val request = requestBuilder.build()
                    chain.proceed(request)
                }.build()


            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_SQUARE_API)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(SquareRestaurantApi::class.java)

        }


}