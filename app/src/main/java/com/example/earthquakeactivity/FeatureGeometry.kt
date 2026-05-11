package com.example.earthquakeactivity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeatureGeometry(
    val type : String,
    val coordinates : ArrayList<Double>
) : Parcelable