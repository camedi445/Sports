package com.cmedina.condorlabs.data.repository

import com.cmedina.condorlabs.data.model.Event
import com.cmedina.condorlabs.data.model.Team

interface TeamDataSource {
    suspend fun fetchTeamsByLeagueId(leagueId: String): OperationResult<List<Team>>?
    suspend fun fetchTeamDetailsById(teamId: String): OperationResult<Team>?
    suspend fun fetchEventsByTeamId(teamId: String) : OperationResult<List<Event>>?
}