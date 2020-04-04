package com.jamieadkins.acnh.data.fish

import androidx.annotation.Keep

@Keep
class FirebaseFish {
    var id: String? = null
    var name: String? = null
    var image: String? = null
    var price: String? = null
    var size: String? = null
    var location: String? = null
    var startHour: Int? = null
    var endHour: Int? = null
    var timeRange: String? = null
    var northernHemisphereMonths: List<Int>? = null
    var southernHemisphereMonths: List<Int>? = null
}