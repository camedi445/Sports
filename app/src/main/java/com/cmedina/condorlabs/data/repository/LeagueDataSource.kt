package com.cmedina.condorlabs.data.repository

import com.cmedina.condorlabs.data.model.League

interface LeagueDataSource {

    suspend fun fetchLeagues(): OperationResult<List<League>>?

}