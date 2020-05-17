package com.cmedina.condorlabs.view.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmedina.condorlabs.R
import com.cmedina.condorlabs.core.loadUrl
import com.cmedina.condorlabs.data.model.Team
import kotlinx.android.synthetic.main.team_item.view.*


class TeamAdapter(val itemTapListener: OnItemTapListener) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    private lateinit var teamList: List<Team>


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        vh.bind(teamList[position])
    }

    override fun getItemCount() = if (::teamList.isInitialized) teamList.size else 0

    fun updateTeamList(teamList: List<Team>) {
        this.teamList = teamList
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(team: Team) {
            itemView.iv_team_badge.loadUrl(team.strTeamBadge)
            itemView.tv_team_name.text = team.strTeam
            itemView.tv_team_stadium.text = team.strStadium
            itemView.setOnClickListener { itemTapListener.onItemSelected(team) }
        }
    }

    interface OnItemTapListener {
        fun onItemSelected(selectedTeam: Team)
    }


}

