package com.cmedina.condorlabs.data.model

data class LeaguesResponse(
    val leagues: List<League> = emptyList()
)

data class League(
    val idLeague: String,
    val strLeague: String? = null
)
