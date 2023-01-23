package fr.android.steam.models

import android.os.Parcel
import android.os.Parcelable

data class ApplicationUser(
    val id: String?,
    val username: String?,
    val email: String?,
    val password: String?,
    var likedGames: List<Game>?,
    val wishListedGames: List<Game>?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Game.CREATOR),
        parcel.createTypedArrayList(Game.CREATOR)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeTypedList(likedGames)
        parcel.writeTypedList(wishListedGames)
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
