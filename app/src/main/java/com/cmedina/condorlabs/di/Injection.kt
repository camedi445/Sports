package com.cmedina.condorlabs.di

import com.cmedina.condorlabs.data.network.SportApi
import com.cmedina.condorlabs.data.network.RetrofitBuilder.retrofitInstance
import com.cmedina.condorlabs.data.repository.LeagueDataSource
import com.cmedina.condorlabs.data.repository.LeagueRepository
import com.cmedina.condorlabs.data.repository.TeamDataSource
import com.cmedina.condorlabs.data.repository.TeamRepository

object Injection {

    private val sportApi: SportApi by lazy {
        retrofitInstance.create(SportApi::class.java)
    }

    val leagueRepository: LeagueDataSource by lazy {
        LeagueRepository(sportApi)
    }

    val teamRepository: TeamDataSource by lazy {
        TeamRepository(sportApi)
    }

}