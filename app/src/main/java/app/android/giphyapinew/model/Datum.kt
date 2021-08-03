package app.android.giphyapinew.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Datum(
        @SerializedName("title")
        @Expose
        val title: String? = null,
        @SerializedName("images")
        @Expose
        val images: Images? = null
) : Parcelable