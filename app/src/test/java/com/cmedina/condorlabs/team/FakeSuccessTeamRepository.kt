package com.cmedina.condorlabs.team

import com.cmedina.condorlabs.data.model.Event
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.data.repository.OperationResult
import com.cmedina.condorlabs.data.repository.TeamDataSource

class FakeSuccessTeamRepository : TeamDataSource {

    val mockList: MutableList<Team> = mutableListOf()
    val eventMockList = mutableListOf(Event("Match 1"), Event("Match 2"))

    init {
        mockData()
    }

    private fun mockData() {
        mockList.add(Team("0", "Team 1"))
        mockList.add(Team("1", "Team 2"))
    }

    override suspend fun fetchTeamsByLeagueId(leagueId: String): OperationResult<List<Team>>? {
        return OperationResult.Success(mockList)
    }

    override suspend fun fetchTeamDetailsById(teamId: String): OperationResult<Team>? {
        return OperationResult.Success(mockList.first())
    }

    override suspend fun fetchEventsByTeamId(teamId: String): OperationResult<List<Event>>? {
        return OperationResult.Success(eventMockList)
    }

}