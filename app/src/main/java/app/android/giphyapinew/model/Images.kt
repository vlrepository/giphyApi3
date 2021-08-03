package app.android.giphyapinew.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    @SerializedName("fixed_height_downsampled")
    @Expose
    val fixedHeightDownSampled: Size? = null
) : Parcelable
