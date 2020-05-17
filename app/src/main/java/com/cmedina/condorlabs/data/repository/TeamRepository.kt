package com.cmedina.condorlabs.data.repository

import com.cmedina.condorlabs.data.model.Event
import com.cmedina.condorlabs.data.model.Team
import com.cmedina.condorlabs.data.network.SportApi

class TeamRepository(private val sportApi: SportApi) : TeamDataSource {

    override suspend fun fetchTeamsByLeagueId(leagueId: String): OperationResult<List<Team>>? {
        return try {
            OperationResult.Success(sportApi.fetchTeamsByLeagueId(leagueId).teams)
        } catch (exception: Throwable) {
            OperationResult.Error(exception = exception)
        }
    }

    override suspend fun fetchTeamDetailsById(teamId: String): OperationResult<Team>? {
        return try {
            OperationResult.Success(sportApi.fetchTeamDetailsById(teamId).teams.first())
        } catch (exception: Throwable) {
            OperationResult.Error(exception = exception)
        }
    }

    override suspend fun fetchEventsByTeamId(teamId: String): OperationResult<List<Event>>? {
        return try {
            OperationResult.Success(sportApi.fetchEventsByTeamId(teamId).events)
        } catch (exception: Throwable) {
            OperationResult.Error(exception = exception)
        }
    }

}