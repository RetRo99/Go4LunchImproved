package com.example.go4lunchimproved.model

import com.example.go4lunchimproved.utils.getCurrentDate

data class User(
    val uid: String? = "",
    var name: String? = "",
    val email: String? = "",
    val photoUrl: String? = "",
    var restaurantId:String? = "",
     var pickedRestaurantText:String? = "",
    var pickedDate:String = ""
){
    
    fun getUserRestaurant(): String {
        return if ((pickedRestaurantText!!.isEmpty()) || pickedDate != getCurrentDate())  "${name?.substringBefore(" ")} hasn't decided yet" else pickedRestaurantText.toString()

    }

    fun matchesCriteria(criteria:String):Boolean{
        return name!!.contains(criteria, true) || pickedRestaurantText!!.substringAfter("(").substringBefore(")").contains(criteria, true)
    }

}


