package com.jamieadkins.acnh.domain.bugs

data class BugEntity(
    val id: String,
    val name: String,
    val imageUrl: String,
    val location: String,
    val price: String,
    val startHour: Int,
    val endHour: Int,
    val timeRange: String,
    val months: List<Int>
)