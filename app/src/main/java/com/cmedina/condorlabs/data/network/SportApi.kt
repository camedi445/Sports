package com.cmedina.condorlabs.data.network

import com.cmedina.condorlabs.data.model.EventsResponse
import com.cmedina.condorlabs.data.model.LeaguesResponse
import com.cmedina.condorlabs.data.model.TeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SportApi {

    @GET("all_leagues.php")
    suspend fun fetchLeagues(): LeaguesResponse

    @GET("lookup_all_teams.php")
    suspend fun fetchTeamsByLeagueId(@Query("id") leagueId: String): TeamsResponse

    @GET("lookupteam.php")
    suspend fun fetchTeamDetailsById(@Query("id") teamId: String): TeamsResponse

    @GET("eventsnext.php")
    suspend fun fetchEventsByTeamId(@Query("id") teamId: String): EventsResponse

}