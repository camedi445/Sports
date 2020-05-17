package com.cmedina.condorlabs.view.team

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cmedina.condorlabs.R
import com.cmedina.condorlabs.core.BaseActivity
import com.cmedina.condorlabs.core.Query
import com.cmedina.condorlabs.core.gone
import com.cmedina.condorlabs.core.visible
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.di.Injection
import com.cmedina.condorlabs.view.league.FilterLeagueActivity
import com.cmedina.condorlabs.viewmodel.TeamListViewModel
import kotlinx.android.synthetic.main.activity_team_list.*
import kotlin.math.roundToInt

class TeamListActivity : BaseActivity(), TeamAdapter.OnItemTapListener {

    private lateinit var teamListViewModel: TeamListViewModel
    private val teamAdapter = TeamAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_list)
        // Instance TeamListViewModel
        createTeamListViewModel()
        teamListViewModel.teamList.observe(this, renderTeamList)
        teamListViewModel.isViewLoading.observe(this, isViewLoadingObserver)
        teamListViewModel.onMessageError.observe(this, onMessageErrorObserver)
        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        rv_teams.apply {
            adapter = teamAdapter
        }
        rv_teams.layoutManager = GridLayoutManager(this, 2)
        rv_teams.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                dpToPx(), true
            )
        )
    }

    override fun onResume() {
        super.onResume()
        tv_selected_league.text = Query.selectedLeague.strLeague
        teamListViewModel.getTeamsByLeagueId(Query.selectedLeague.idLeague)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        if (it) {
            rv_teams.gone()
            progressBar.visible()
        } else {
            progressBar.gone()
            rv_teams.visible()
        }
    }

    private val onMessageErrorObserver = Observer<Any> {
        layoutError.visible()
    }


    private fun createTeamListViewModel() {
        // Parent base activity view model factory
        val factory = viewModelFactory {
            TeamListViewModel(Injection.teamRepository)
        }
        teamListViewModel = ViewModelProvider(
            this,
            factory
        ).get(TeamListViewModel::class.java)
    }

    private val renderTeamList = Observer<List<Team>> {
        rv_teams.smoothScrollToPosition(0)
        teamAdapter.updateTeamList(it)
    }

    private fun setupListeners() {
        tv_lbl_change_league.setOnClickListener {
            startActivity(FilterLeagueActivity.newIntent(this))
        }
    }

    private fun dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            16f,
            resources.displayMetrics
        ).roundToInt()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, TeamListActivity::class.java)
    }

    override fun onItemSelected(selectedTeam: Team) {
        val intent = TeamDetailActivity.newIntent(this)
        intent.putExtra(TeamDetailActivity.TEAM_ID, selectedTeam.idTeam)
        startActivity(intent)
    }
}