package com.cmedina.condorlabs.data.repository

import com.cmedina.condorlabs.data.model.League
import com.cmedina.condorlabs.data.network.SportApi

class LeagueRepository(private val sportApi: SportApi) : LeagueDataSource {

    override suspend fun fetchLeagues(): OperationResult<List<League>>? {
        return try {
            OperationResult.Success(sportApi.fetchLeagues().leagues)
        } catch (exception: Throwable) {
            OperationResult.Error(exception = exception)
        }
    }

}