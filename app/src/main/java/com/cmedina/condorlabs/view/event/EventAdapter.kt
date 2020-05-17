package com.cmedina.condorlabs.view.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmedina.condorlabs.R
import com.cmedina.condorlabs.data.model.Event
import kotlinx.android.synthetic.main.event_item.view.*

class EventAdapter() :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private lateinit var eventList: List<Event>


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        vh.bind(eventList[position])
    }

    override fun getItemCount() = if (::eventList.isInitialized) eventList.size else 0

    fun updateEventList(eventList: List<Event>) {
        this.eventList = eventList
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(event: Event) {
            itemView.tv_event_name.text = event.strEvent
        }
    }


}

