package com.jamieadkins.acnh.domain.fish

data class FishEntity(
    val id: String,
    val name: String,
    val location: String,
    val price: String,
    val size: String,
    val startHour: Int,
    val endHour: Int,
    val timeRange: String,
    val months: List<Int>
)