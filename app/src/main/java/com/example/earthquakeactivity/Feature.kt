package com.example.earthquakeactivity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Feature(
    val type: String,
    val properties : FeatureProperties,
    val geometry : FeatureGeometry,
    val id: String
) : Parcelable
