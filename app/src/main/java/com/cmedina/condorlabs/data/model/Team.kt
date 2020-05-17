package com.cmedina.condorlabs.data.model


data class TeamsResponse(
    val teams: List<Team> = emptyList()
)

data class Team(
    val idTeam: String,
    val strTeam: String? = null,
    val strTeamBadge: String? = null,
    val strStadium: String? = null,
    val strTeamJersey: String? = null,
    val intFormedYear: String? = null,
    val strDescriptionEN: String? = null,
    val strYoutube: String? = null,
    val strWebsite: String? = null,
    val strFacebook: String? = null,
    val strTwitter: String? = null,
    val strInstagram: String? = null

)

