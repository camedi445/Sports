package com.cmedina.condorlabs.view.team

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmedina.condorlabs.R
import com.cmedina.condorlabs.core.*
import com.cmedina.condorlabs.data.model.Event
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.di.Injection
import com.cmedina.condorlabs.view.event.EventAdapter
import com.cmedina.condorlabs.viewmodel.TeamDetailViewModel
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : BaseActivity() {

    private lateinit var teamDetailViewModel: TeamDetailViewModel
    private val eventAdapter = EventAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        // Instance TeamListViewModel
        createTeamListViewModel()
        if (intent.extras != null) {
            val teamId = intent.getStringExtra(TEAM_ID)
            teamId?.let {
                setupUI()
                setupViewModel(teamId)
            }
        }
    }

    private fun setupViewModel(teamId: String) {
        teamDetailViewModel.eventList.observe(this, renderEventList)
        teamDetailViewModel.teamData.observe(this, renderTeamData)
        teamDetailViewModel.isViewLoading.observe(this, isViewLoadingObserver)
        teamDetailViewModel.onMessageError.observe(this, onMessageErrorObserver)
        teamDetailViewModel.getTeamDetail(teamId)
        teamDetailViewModel.getEventsByTeamId(teamId)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        if (it) {
            progressBar.visible()
            ly_team_detail.gone()
        } else {
            progressBar.gone()
            ly_team_detail.visible()
        }
    }

    private val onMessageErrorObserver = Observer<Any> {
        layoutError.visible()
    }

    private val renderEventList = Observer<List<Event>> {
        eventAdapter.updateEventList(it)
    }

    private val renderTeamData = Observer<Team> {
        iv_team_background.loadUrl(it.strTeamJersey)
        iv_team_badge.loadUrl(it.strTeamBadge)
        val nameAndDate = "${it.strTeam} - ${it.intFormedYear}"
        tv_team_name.text = nameAndDate
        tv_team_desc.text = it.strDescriptionEN
        setupSocialNetwork(it)
    }

    private fun setupSocialNetwork(it: Team) {
        iv_facebook.setUrl(it.strFacebook)
        iv_twitter.setUrl(it.strTwitter)
        iv_youtube.setUrl(it.strYoutube)
        iv_web_site.setUrl(it.strWebsite)
        iv_instagram.setUrl(it.strInstagram)
    }

    private fun setupUI() {
        iv_close.setOnClickListener {
            finish()
        }
        rv_events.apply {
            adapter = eventAdapter
        }
        rv_events.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun createTeamListViewModel() {
        // Parent base activity view model factory
        val factory = viewModelFactory {
            TeamDetailViewModel(Injection.teamRepository)
        }
        teamDetailViewModel = ViewModelProvider(
            this,
            factory
        ).get(TeamDetailViewModel::class.java)
    }

    companion object {
        const val TEAM_ID = "team_id"
        fun newIntent(context: Context) = Intent(context, TeamDetailActivity::class.java)
    }
}