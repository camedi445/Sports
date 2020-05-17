package com.cmedina.condorlabs.data.model


data class EventsResponse(
    val events: List<Event> = emptyList()
)

data class Event(
    val strEvent: String? = null
)