package com.jamieadkins.acnh.fish

import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.ToggleFishCaughtUseCase
import javax.inject.Inject

class FishCaughtPresenter @Inject constructor(
    private val toggleFishCaughtUseCase: ToggleFishCaughtUseCase
) : FishCaughtContract {

    override fun onFishCaughtToggled(fish: FishEntity) = toggleFishCaughtUseCase.toggle(fish)

}