package com.cmedina.condorlabs.view.league

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmedina.condorlabs.R
import com.cmedina.condorlabs.data.model.League
import kotlinx.android.synthetic.main.league_item.view.*
import java.util.*

class LeagueAdapter(val itemTapListener: OnItemTapListener) :
    RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    private lateinit var leagueList: List<League>
    private var leagueListFiltered = listOf<League>()


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.league_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        vh.bind(leagueListFiltered[position])
    }

    override fun getItemCount() = leagueListFiltered.size

    fun updateLeagueList(leagueList: List<League>) {
        this.leagueList = leagueList
        this.leagueListFiltered = leagueList
        notifyDataSetChanged()
    }


    fun filter(query: String?) {
        leagueListFiltered = if (query.isNullOrEmpty()) {
            leagueList
        } else {
            leagueList.filter {
                !it.strLeague.isNullOrEmpty() &&
                        it.strLeague.toLowerCase(Locale.ROOT).contains(query)
            }
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(league: League) {
            itemView.tv_league_name.text = league.strLeague
            itemView.setOnClickListener { itemTapListener.onItemSelected(league) }
        }
    }

    interface OnItemTapListener {
        fun onItemSelected(selectedLeague: League)
    }


}

