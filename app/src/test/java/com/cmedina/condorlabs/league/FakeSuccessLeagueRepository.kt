package com.cmedina.condorlabs.league

import com.cmedina.condorlabs.data.model.League
import com.cmedina.condorlabs.data.repository.LeagueDataSource
import com.cmedina.condorlabs.data.repository.OperationResult

class FakeSuccessLeagueRepository : LeagueDataSource {

    val mockList: MutableList<League> = mutableListOf()

    init {
        mockData()
    }

    private fun mockData() {
        mockList.add(League("0", "League 1"))
        mockList.add(League("1", "League 2"))
    }


    override suspend fun fetchLeagues(): OperationResult<List<League>>? {
        return OperationResult.Success(mockList)
    }

}