package app.android.giphyapinew.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response (
    @SerializedName("data")
    @Expose
    var data: List<Datum>
)