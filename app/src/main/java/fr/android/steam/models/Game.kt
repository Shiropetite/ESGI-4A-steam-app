package fr.android.steam.models

import android.os.Parcel
import android.os.Parcelable

data class Game(
    val name: String?,
    val description: String?,
    val publisher: String?,
    val price: String?,
    val mini_image: String?,
    val background_image: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(publisher)
        parcel.writeString(price)
        parcel.writeString(mini_image)
        parcel.writeString(background_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }
}
