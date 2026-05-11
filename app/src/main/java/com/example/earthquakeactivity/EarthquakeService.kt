package com.example.earthquakeactivity

import retrofit2.Call
import retrofit2.http.GET

interface EarthquakeService {
    // where you list out the different endpoints in the api you want to call
    // function returns Call<type> here type is the data returned in the json
    // in the @GET("blah"), "blah" is the path to the file(endpoint)
    @GET(value = "summary/all_day.geojson")
    fun getEarthquakeDataPastDay() : Call<FeatureCollection>
}