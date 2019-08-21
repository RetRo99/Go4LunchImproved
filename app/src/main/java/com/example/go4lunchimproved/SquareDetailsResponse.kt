package com.example.go4lunchimproved

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SquareDetailsResponse(

        @SerializedName("response") val response : ResponseDetail

)

data class ResponseDetail (

        @SerializedName("venue") val venue : Venue
)

data class Venue(

    @SerializedName("location") val location: SquareLocation,
    @SerializedName("name") val name: String,
    @SerializedName("categories") val categories: List<Categorie>,
    @SerializedName("distance") var distance: String,
    @SerializedName("contact") val contact: Contact,
    @SerializedName("description") val description: String,
    @SerializedName("page") val page: Page,
    @SerializedName("hours") val hours: Hours,
    @SerializedName("bestPhoto") val bestPhoto: BestPhoto
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<SquareLocation>(SquareLocation::class.java.classLoader)!!,
        source.readString()!!,
        ArrayList<Categorie>().apply { source.readList(this as List<*>, Categorie::class.java.classLoader) },
        source.readString()!!,
        source.readParcelable<Contact>(Contact::class.java.classLoader)!!,
        source.readString()!!,
        source.readParcelable<Page>(Page::class.java.classLoader)!!,
        source.readParcelable<Hours>(Hours::class.java.classLoader)!!,
        source.readParcelable<BestPhoto>(BestPhoto::class.java.classLoader)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(location, 0)
        writeString(name)
        writeList(categories)
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
}

data class Contact(

    @SerializedName("formattedPhone") val phone: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!
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

    @SerializedName("prefix") val prefix: String,
    @SerializedName("suffix") val suffix: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(prefix)
        writeString(suffix)
        writeInt(width)
        writeInt(height)
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

    @SerializedName("pageInfo") val pageInfo: PageInfo
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<PageInfo>(PageInfo::class.java.classLoader)!!
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

    @SerializedName("description") val description: String,
    @SerializedName("banner") val banner: String,
    @SerializedName("links") val links: Links
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readParcelable<Links>(Links::class.java.classLoader)!!
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

    @SerializedName("count") val count: Int,
    @SerializedName("items") val items: List<Items>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.createTypedArrayList(Items.CREATOR)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(count)
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

    @SerializedName("url") val url: String

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!
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

    @SerializedName("status") val status: String,
    @SerializedName("richStatus") val richStatus: RichStatus,
    @SerializedName("isOpen") val isOpen: Boolean,
    @SerializedName("isLocalHoliday") val isLocalHoliday: Boolean,
    @SerializedName("dayData") val dayData: List<String>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readParcelable<RichStatus>(RichStatus::class.java.classLoader)!!,
        1 == source.readInt(),
        1 == source.readInt(),
        source.createStringArrayList()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(status)
        writeParcelable(richStatus, 0)
        writeInt((if (isOpen) 1 else 0))
        writeInt((if (isLocalHoliday) 1 else 0))
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

    @SerializedName("entities") val entities: List<String>,
    @SerializedName("text") val text: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.createStringArrayList()!!,
        source.readString()!!
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