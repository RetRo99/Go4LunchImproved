package com.example.go4lunchimproved

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SquareDetailsResponse(

    @SerializedName("response") val response: ResponseDetail?

)

data class ResponseDetail(

    @SerializedName("venue") val venue: Venue?
)

data class Venue(

    @SerializedName("location") val location: SquareLocation,
    @SerializedName("name") val name: String?,
    @SerializedName("categories") val categories: List<Categorie>?,
    @SerializedName("distance") var distance: String?,
    @SerializedName("contact") val contact: Contact?,
    @SerializedName("description") val description: String?,
    @SerializedName("page") val page: Page?,
    @SerializedName("hours") val hours: Hours?,
    @SerializedName("bestPhoto") val bestPhoto: BestPhoto?
) : Parcelable, Comparable<Venue> {
    override fun compareTo(other: Venue): Int {
        return if (other.location.distance!! > this.location.distance!!) -1 else 1
    }


    constructor(source: Parcel) : this(
        source.readParcelable<SquareLocation>(SquareLocation::class.java.classLoader)!!,
        source.readString(),
        source.createTypedArrayList(Categorie.CREATOR),
        source.readString(),
        source.readParcelable<Contact>(Contact::class.java.classLoader),
        source.readString(),
        source.readParcelable<Page>(Page::class.java.classLoader),
        source.readParcelable<Hours>(Hours::class.java.classLoader),
        source.readParcelable<BestPhoto>(BestPhoto::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(location, 0)
        writeString(name)
        writeTypedList(categories)
        writeString(distance)
        writeParcelable(contact, 0)
        writeString(description)
        writeParcelable(page, 0)
        writeParcelable(hours, 0)
        writeParcelable(bestPhoto, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Venue> = object : Parcelable.Creator<Venue> {
            override fun createFromParcel(source: Parcel): Venue = Venue(source)
            override fun newArray(size: Int): Array<Venue?> = arrayOfNulls(size)
        }
    }

    fun getPhotoUrl(): String {
        return "${bestPhoto?.prefix}1920x1080${bestPhoto?.suffix}"
    }

    fun getWebsite(): String? {
        return page?.pageInfo?.links?.items?.get(0)?.url
    }

    fun getPhoneNumber(): String? {
        return contact?.phone

    }

    fun getAddressText(): String {
        return if (!location.address.isNullOrEmpty()) {
            "${categories?.get(0)?.name} - ${location.address}"
        } else {
            "${categories?.get(0)?.name}"
        }
    }

    fun getDistanceText(): String {
        return "${location.distance}  m"
    }

    fun getOpeningHours(): String {
        return if (!hours?.status.isNullOrEmpty()) "${hours?.status}" else "No opening hours avaliable"
    }
}


data class Categorie(

    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("pluralName") val pluralName: String?,
    @SerializedName("shortName") val shortName: String?,
    @SerializedName("primary") val primary: Boolean?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(pluralName)
        writeString(shortName)
        writeValue(primary)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Categorie> = object : Parcelable.Creator<Categorie> {
            override fun createFromParcel(source: Parcel): Categorie = Categorie(source)
            override fun newArray(size: Int): Array<Categorie?> = arrayOfNulls(size)
        }
    }
}

data class SquareLocation(
    @SerializedName("address") val address: String?,
    @SerializedName("crossStreet") val crossStreet: String?,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("distance") var distance: Int?,
    @SerializedName("postalCode") val postalCode: Int?,
    @SerializedName("cc") val cc: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("formattedAddress") val formattedAddress: List<String>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readValue(Double::class.java.classLoader)!! as Double,
        source.readValue(Double::class.java.classLoader)!! as Double,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createStringArrayList()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(address)
        writeString(crossStreet)
        writeValue(lat)
        writeValue(lng)
        writeValue(distance)
        writeValue(postalCode)
        writeString(cc)
        writeString(city)
        writeString(state)
        writeString(country)
        writeStringList(formattedAddress)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SquareLocation> =
            object : Parcelable.Creator<SquareLocation> {
                override fun createFromParcel(source: Parcel): SquareLocation =
                    SquareLocation(source)

                override fun newArray(size: Int): Array<SquareLocation?> = arrayOfNulls(size)
            }
    }
}

data class Contact(

    @SerializedName("formattedPhone") val phone: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(phone)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Contact> = object : Parcelable.Creator<Contact> {
            override fun createFromParcel(source: Parcel): Contact = Contact(source)
            override fun newArray(size: Int): Array<Contact?> = arrayOfNulls(size)
        }
    }
}

data class BestPhoto(

    @SerializedName("prefix") val prefix: String?,
    @SerializedName("suffix") val suffix: String?,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(prefix)
        writeString(suffix)
        writeValue(width)
        writeValue(height)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BestPhoto> = object : Parcelable.Creator<BestPhoto> {
            override fun createFromParcel(source: Parcel): BestPhoto = BestPhoto(source)
            override fun newArray(size: Int): Array<BestPhoto?> = arrayOfNulls(size)
        }
    }
}

data class Page(

    @SerializedName("pageInfo") val pageInfo: PageInfo?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<PageInfo>(PageInfo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(pageInfo, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Page> = object : Parcelable.Creator<Page> {
            override fun createFromParcel(source: Parcel): Page = Page(source)
            override fun newArray(size: Int): Array<Page?> = arrayOfNulls(size)
        }
    }
}

data class PageInfo(

    @SerializedName("description") val description: String?,
    @SerializedName("banner") val banner: String?,
    @SerializedName("links") val links: Links?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readParcelable<Links>(Links::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(description)
        writeString(banner)
        writeParcelable(links, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PageInfo> = object : Parcelable.Creator<PageInfo> {
            override fun createFromParcel(source: Parcel): PageInfo = PageInfo(source)
            override fun newArray(size: Int): Array<PageInfo?> = arrayOfNulls(size)
        }
    }
}

data class Links(

    @SerializedName("count") val count: Int?,
    @SerializedName("items") val items: List<Items>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.createTypedArrayList(Items.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(count)
        writeTypedList(items)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Links> = object : Parcelable.Creator<Links> {
            override fun createFromParcel(source: Parcel): Links = Links(source)
            override fun newArray(size: Int): Array<Links?> = arrayOfNulls(size)
        }
    }
}

data class Items(

    @SerializedName("url") val url: String?

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Items> = object : Parcelable.Creator<Items> {
            override fun createFromParcel(source: Parcel): Items = Items(source)
            override fun newArray(size: Int): Array<Items?> = arrayOfNulls(size)
        }
    }
}

data class Hours(

    @SerializedName("status") val status: String?,
    @SerializedName("richStatus") val richStatus: RichStatus?,
    @SerializedName("isOpen") val isOpen: Boolean?,
    @SerializedName("isLocalHoliday") val isLocalHoliday: Boolean?,
    @SerializedName("dayData") val dayData: List<String>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readParcelable<RichStatus>(RichStatus::class.java.classLoader),
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.createStringArrayList()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(status)
        writeParcelable(richStatus, 0)
        writeValue(isOpen)
        writeValue(isLocalHoliday)
        writeStringList(dayData)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Hours> = object : Parcelable.Creator<Hours> {
            override fun createFromParcel(source: Parcel): Hours = Hours(source)
            override fun newArray(size: Int): Array<Hours?> = arrayOfNulls(size)
        }
    }
}

data class RichStatus(

    @SerializedName("entities") val entities: List<String>?,
    @SerializedName("text") val text: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.createStringArrayList(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeStringList(entities)
        writeString(text)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RichStatus> = object : Parcelable.Creator<RichStatus> {
            override fun createFromParcel(source: Parcel): RichStatus = RichStatus(source)
            override fun newArray(size: Int): Array<RichStatus?> = arrayOfNulls(size)
        }
    }
}