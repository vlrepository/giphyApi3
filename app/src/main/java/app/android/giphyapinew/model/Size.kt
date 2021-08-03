package app.android.giphyapinew.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Size(
        @SerializedName("height")
        @Expose
        var height: String? = null,
        @SerializedName("url")
        @Expose
        var url: String? = null,
        @SerializedName("width")
        @Expose
        var width: String? = null
) : Parcelable