package com.example.earthquakeactivity

data class FeatureCollection(
    val type: String,
    val metadata : FeatureMetadeta,
    var features : ArrayList<Feature>
)