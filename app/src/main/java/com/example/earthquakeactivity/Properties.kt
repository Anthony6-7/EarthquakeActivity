package com.example.earthquakeactivity

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeatureProperties(
    val mag : Float,
    val place : String,
    var time : String,
    val url : String,
    val title : String
) : Parcelable
