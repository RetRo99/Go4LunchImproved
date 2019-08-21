package com.example.go4lunchimproved

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SquareRestaurantResponse(

    @SerializedName("response") val response : Response
)


data class Response (

    @SerializedName("venues") val venues : List<Venues>
)

data class Venues (

    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("location") val location : SquareLocation,
    @SerializedName("categories")  val categories : List<Categorie>,
    @SerializedName("referralId") val referralId : String,
    @SerializedName("hasPerk") val hasPerk : Boolean
)

data class Categorie (

    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("pluralName") val pluralName : String,
    @SerializedName("shortName") val shortName : String,
    @SerializedName("primary") val primary : Boolean
)

data class SquareLocation(

    @SerializedName("address") val address: String,
    @SerializedName("crossStreet") val crossStreet: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("distance") var distance: Int,
    @SerializedName("postalCode") val postalCode: Int,
    @SerializedName("cc") val cc: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("country") val country: String,
    @SerializedName("formattedAddress") val formattedAddress: List<String>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readDouble(),
        source.readDouble(),
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.createStringArrayList()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(address)
        writeString(crossStreet)
        writeDouble(lat)
        writeDouble(lng)
        writeInt(distance)
        writeInt(postalCode)
        writeString(cc)
        writeString(city)
        writeString(state)
        writeString(country)
        writeStringList(formattedAddress)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SquareLocation> = object : Parcelable.Creator<SquareLocation> {
            override fun createFromParcel(source: Parcel): SquareLocation = SquareLocation(source)
            override fun newArray(size: Int): Array<SquareLocation?> = arrayOfNulls(size)
        }
    }
}