package com.example.earthquakeactivity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.earthquakeactivity.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneId
import java.util.TimeZone

class EarthquakeListActivity : AppCompatActivity() {
    private lateinit var customAdapter : EarthquakeAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val earthquakeService = RetrofitHelper.getInstance().create(EarthquakeService::class.java)
        val earthquakeCall = earthquakeService.getEarthquakeDataPastDay()
        earthquakeCall.enqueue(object : Callback<FeatureCollection> {
            override fun onResponse(
                call: Call<FeatureCollection>,
                response: Response<FeatureCollection>
            ) {
                var earthquakeData = response.body()
                Log.d("EarthquakeList", "data : ${earthquakeData}")

                if (earthquakeData != null) {
                    customAdapter = EarthquakeAdapter(earthquakeData.features)
                    customAdapter.earthquakeList = customAdapter.earthquakeList.filter { it.properties.mag >= 1 }
                }
                binding.EarthquakeRecyclerView.adapter = customAdapter
                binding.EarthquakeRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@EarthquakeListActivity)

            }

            override fun onFailure(call: Call<FeatureCollection>, t: Throwable) {
                Log.d("EarthquakeList", "onFailure: ${t.message}")
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.earthquake_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.sort_recent -> {
                customAdapter.earthquakeList = customAdapter.earthquakeList.sortedBy { it.properties.time }
                customAdapter.notifyDataSetChanged()
                true
            }
            R.id.sort_mag -> {
                customAdapter.earthquakeList = customAdapter.earthquakeList.sortedBy { -it.properties.mag }
                customAdapter.notifyDataSetChanged()
                true
            }
            R.id.button_help -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Help")
                builder.setMessage("Significant (Purple)  >6.5\n" +
                        "Large (Red) 4.6-6.5\n" +
                        "Orange (Moderate) 2.5-4.5\n" +
                        "Small (Blue) 1.0-2.5")
                val dialog = builder.create()
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}