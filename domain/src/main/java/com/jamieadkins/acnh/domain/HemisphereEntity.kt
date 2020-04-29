package com.jamieadkins.acnh.domain

sealed class HemisphereEntity {

    object Northern : HemisphereEntity()
    object Southern : HemisphereEntity()
}