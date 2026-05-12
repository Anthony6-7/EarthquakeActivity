package com.example.earthquakeactivity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.preference.PreferenceManager
import android.text.Layout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.earthquakeactivity.databinding.ActivityEarthquakeMapBinding
import com.example.earthquakeactivity.databinding.ActivityMainBinding

import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow

class EarthquakeMapActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var binding: ActivityEarthquakeMapBinding
    private lateinit var inflater : LayoutInflater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEarthquakeMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        var earthquake = intent.getParcelableExtra<Feature>(EarthquakeAdapter.EXTRA_EARTHQUAKE)

        val mapController =  binding.map.controller

        mapController.setZoom(9.5)

        val startPoint = GeoPoint(earthquake!!.geometry.coordinates[1], earthquake.geometry.coordinates[0])

        mapController.setCenter(startPoint);

        val earthquakeMarker = Marker(binding.map)


        earthquakeMarker.title = earthquake.properties.title

        earthquakeMarker.position = GeoPoint(earthquake.geometry.coordinates[1], earthquake.geometry.coordinates[0])


//        val earthquakeInfoWindow = MarkerInfoWindow(R.layout.activity_earthquake_map, binding.map)
//        earthquakeMarker.infoWindow = earthquakeInfoWindow
        earthquakeMarker.snippet = "Magnitude : ${earthquake.properties.mag}, Location: ${earthquake.geometry.coordinates}"

        binding.map.overlays.add(earthquakeMarker)

    }

//    @Override
//    public override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        binding.map = MapView(inflater.getContext(), MapTileProviderBasic(inflater.getContext()));
//        return  binding.map
//    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        binding.map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        binding.map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }






}