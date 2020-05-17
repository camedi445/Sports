package com.cmedina.condorlabs.view.league

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmedina.condorlabs.R
import com.cmedina.condorlabs.core.BaseActivity
import com.cmedina.condorlabs.core.Query
import com.cmedina.condorlabs.core.gone
import com.cmedina.condorlabs.core.visible
import com.cmedina.condorlabs.data.model.League
import com.cmedina.condorlabs.di.Injection
import com.cmedina.condorlabs.viewmodel.LeagueViewModel
import kotlinx.android.synthetic.main.activity_filter_league.*

class FilterLeagueActivity : BaseActivity(), LeagueAdapter.OnItemTapListener {

    private lateinit var leagueViewModel: LeagueViewModel
    private lateinit var searchView: SearchView
    private val leagueAdapter = LeagueAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_league)
        // Instance LeagueViewModel
        createLeagueViewModel()
        setupUI()
        // get all the leagues
        leagueViewModel.getLeagueList()
        // observe variables
        leagueViewModel.leagueList.observe(this, renderLeagueList)
        leagueViewModel.isViewLoading.observe(this, isViewLoadingObserver)
        leagueViewModel.onMessageError.observe(this, onMessageErrorObserver)
    }

    private fun setupUI() {
        setSupportActionBar(tb_filter_league)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tv_leagues)
        rv_league.layoutManager = LinearLayoutManager(this)
        rv_league.apply {
            adapter = leagueAdapter
        }
        whiteNotificationBar(rv_league)
    }

    private val renderLeagueList = Observer<List<League>> {
        leagueAdapter.updateLeagueList(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        if (it)
            progressBar.visible()
        else
            progressBar.gone()
    }

    private val onMessageErrorObserver = Observer<Any> {
        layoutError.visible()
    }

    private fun createLeagueViewModel() {
        // Parent base activity view model factory
        val factory = viewModelFactory {
            LeagueViewModel(Injection.leagueRepository)
        }
        leagueViewModel = ViewModelProvider(
            this,
            factory
        ).get(LeagueViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.let {

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView
            searchView.setSearchableInfo(
                searchManager
                    .getSearchableInfo(componentName)
            )
            searchView.maxWidth = Int.MAX_VALUE
        }
        setupSearchViewListener()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupSearchViewListener() {
        searchView.setOnQueryTextListener(object :

            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                leagueAdapter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                leagueAdapter.filter(query)
                return false
            }

        })
    }


    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, FilterLeagueActivity::class.java)
    }

    private fun whiteNotificationBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            window.statusBarColor = Color.WHITE
        }
    }

    override fun onItemSelected(selectedLeague: League) {
        Query.selectedLeague = selectedLeague
        finish()
    }
}