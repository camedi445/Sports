package com.cmedina.condorlabs.league

import com.cmedina.condorlabs.data.model.League
import com.cmedina.condorlabs.data.repository.LeagueDataSource
import com.cmedina.condorlabs.data.repository.OperationResult

class FakeFailureLeagueRepository:LeagueDataSource {

    private val mockException = Exception("Ocurrió un error")

    override suspend fun fetchLeagues(): OperationResult<List<League>>? {
        return OperationResult.Error(mockException)
    }
}