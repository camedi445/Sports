package com.cmedina.condorlabs.team

import com.cmedina.condorlabs.data.model.Event
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.data.repository.OperationResult
import com.cmedina.condorlabs.data.repository.TeamDataSource

class FakeFailureTeamRepository : TeamDataSource {

    private val mockException = Exception("Ocurrió un error")

    override suspend fun fetchTeamsByLeagueId(leagueId: String): OperationResult<List<Team>>? {
        return OperationResult.Error(mockException)
    }

    override suspend fun fetchTeamDetailsById(teamId: String): OperationResult<Team>? {
        return OperationResult.Error(mockException)
    }

    override suspend fun fetchEventsByTeamId(teamId: String): OperationResult<List<Event>>? {
        return OperationResult.Error(mockException)
    }
}