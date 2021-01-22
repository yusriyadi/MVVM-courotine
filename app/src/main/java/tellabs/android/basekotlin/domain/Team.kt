package tellabs.android.basekotlin.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Team(
    val teamId: String,
    val teamName: String,
    val teamDescription: String,
    val teamStadiumName: String,
    val teamLogo: String
):Parcelable