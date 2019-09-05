package com.example.go4lunchimproved.model

data class User(
    val uid: String? = "",
    var name: String? = "",
    val email: String? = "",
    val photoUrl: String? = "",
    var restaurantId:String? = "",
     var pickedRestaurantText:String? = ""
)


