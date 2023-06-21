package com.example.sicredi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sicredi.R
import com.example.sicredi.model.Event

class EventAdapter(private var events: List<Event>, val onEventClick: (Event) -> Unit) :
    RecyclerView.Adapter<EventAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_event, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(events[position])
        vh.itemView.setOnClickListener {
            onEventClick(events[position])
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun update(data: List<Event>) {
        events = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewName: TextView = view.findViewById(R.id.textViewName)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        fun bind(museum: Event) {
            textViewName.text = museum.title.capitalize()
           Glide.with(imageView.context).load(museum.imageUrl).into(imageView)
        }
    }
}