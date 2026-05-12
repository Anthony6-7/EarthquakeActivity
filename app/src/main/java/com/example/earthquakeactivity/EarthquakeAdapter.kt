package com.example.earthquakeactivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class EarthquakeAdapter (var earthquakeList: List<Feature>):
    RecyclerView.Adapter<EarthquakeAdapter.ViewHolder>() {
        companion object{
            const val EXTRA_EARTHQUAKE = "extra earthquake"
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val location: TextView
        val magnitude: TextView
        val time: TextView
        val layout: ConstraintLayout
        val warningImageView : ImageView
        init{
            location = view.findViewById(R.id.item_earthquake_location)
            magnitude = view.findViewById(R.id.item_earthquake_magnitude)
            time = view.findViewById(R.id.item_earthquake_time)
            layout = view.findViewById(R.id.layout_earthquake)
            warningImageView = view.findViewById(R.id.warningIconImageView)
        }
    }

    fun onCreate(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_earthquake, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_earthquake, parent, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val earthquake = earthquakeList[position]

        val redColor = ContextCompat.getColor(viewHolder.layout.context, R.color.red)
        val blueColor = ContextCompat.getColor(viewHolder.layout.context, R.color.blue)
        val orangeColor = ContextCompat.getColor(viewHolder.layout.context, R.color.orange)
        val purpleColor = ContextCompat.getColor(viewHolder.layout.context, R.color.purple )

        var shortenedMagnitude = earthquake.properties.mag.toString()
        if (shortenedMagnitude.length > 3){
            shortenedMagnitude = shortenedMagnitude.substring(0, 3)
        }
        viewHolder.magnitude.text = shortenedMagnitude
        viewHolder.location.text = earthquake.properties.place
        viewHolder.time.text = earthquake.properties.time.toString()

        if (earthquake.properties.mag < 2.5){
            viewHolder.magnitude.setTextColor(blueColor)
            viewHolder.warningImageView.alpha = 0F
        }else if (earthquake.properties.mag >= 2 && earthquake.properties.mag < 4.5){
            viewHolder.magnitude.setTextColor(orangeColor)
            viewHolder.warningImageView.alpha = 0F
        }else if (earthquake.properties.mag >= 4.6 && earthquake.properties.mag <= 6.5){
            viewHolder.magnitude.setTextColor(redColor)
            viewHolder.warningImageView.alpha = 0.5F
        }else if (earthquake.properties.mag >= 6.5)
        {
            viewHolder.magnitude.setTextColor(purpleColor)
            viewHolder.warningImageView.alpha = 1.0F
        }

        val context = viewHolder.itemView.context
        viewHolder.layout.setOnClickListener {
            val intent = Intent(context, EarthquakeMapActivity::class.java)
            //add the hero to the extras of the intent
            intent.putExtra(EXTRA_EARTHQUAKE, earthquake)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Toast.makeText(context, "${earthquake.properties.title} clicked!", Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = earthquakeList.size

    }