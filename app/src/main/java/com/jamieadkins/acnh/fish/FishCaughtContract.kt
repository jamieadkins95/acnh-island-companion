package com.jamieadkins.acnh.fish

import com.jamieadkins.acnh.domain.fish.FishEntity

interface FishCaughtContract {

    fun onFishCaughtToggled(fish: FishEntity)
}