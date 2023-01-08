package fr.android.steam.models

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class GameReview(
    val name: String?,
    val good_grade: Boolean,
    val text: String?
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString(),
    ) {
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeBoolean(good_grade)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameReview> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): GameReview {
            return GameReview(parcel)
        }

        override fun newArray(size: Int): Array<GameReview?> {
            return arrayOfNulls(size)
        }
    }
}
