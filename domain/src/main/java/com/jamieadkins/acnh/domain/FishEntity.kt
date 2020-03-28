package com.jamieadkins.acnh.domain

data class FishEntity(
    val id: String,
    val name: String,
    val location: String,
    val price: Int,
    val size: String,
    val startHour: Int,
    val endHour: Int,
    val timeRange: String,
    val months: List<Int>
)