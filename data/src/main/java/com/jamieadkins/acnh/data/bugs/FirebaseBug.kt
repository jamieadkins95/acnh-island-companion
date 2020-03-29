package com.jamieadkins.acnh.data.bugs

import androidx.annotation.Keep

@Keep
class FirebaseBug {
    var id: String? = null
    var name: String? = null
    var price: String? = null
    var location: String? = null
    var startHour: Int? = null
    var endHour: Int? = null
    var timeRange: String? = null
    var northernHemisphereMonths: List<Int>? = null
    var southernHemisphereMonths: List<Int>? = null
}