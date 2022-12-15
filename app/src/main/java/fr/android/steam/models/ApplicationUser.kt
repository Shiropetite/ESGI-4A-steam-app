package fr.android.steam.models

import android.os.Parcel
import android.os.Parcelable

data class ApplicationUser(
    val username: String?,
    val email: String?,
    val password: String?,
    val likedGames: List<String>?,
    val wishListedGames: List<String>?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeStringList(likedGames)
        parcel.writeStringList(wishListedGames)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApplicationUser> {
        override fun createFromParcel(parcel: Parcel): ApplicationUser {
            return ApplicationUser(parcel)
        }

        override fun newArray(size: Int): Array<ApplicationUser?> {
            return arrayOfNulls(size)
        }
    }

}
