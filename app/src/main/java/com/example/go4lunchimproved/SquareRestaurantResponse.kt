package com.example.go4lunchimproved

import com.google.gson.annotations.SerializedName

data class SquareRestaurantResponse(

    @SerializedName("response") val response: Response
)


data class Response(

    @SerializedName("venues") val venues: List<Venues>
)

data class Venues(

    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: SquareLocation,
    @SerializedName("categories") val categories: List<Categorie>,
    @SerializedName("referralId") val referralId: String,
    @SerializedName("hasPerk") val hasPerk: Boolean
)




