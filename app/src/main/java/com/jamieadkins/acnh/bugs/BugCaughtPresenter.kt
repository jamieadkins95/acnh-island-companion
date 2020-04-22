package com.jamieadkins.acnh.bugs

import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.bugs.ToggleBugsCaughtUseCase
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.ToggleFishCaughtUseCase
import javax.inject.Inject

class BugCaughtPresenter @Inject constructor(
    private val toggleBugsCaughtUseCase: ToggleBugsCaughtUseCase
) : BugsCaughtContract {

    override fun onBugCaughtToggled(bug: BugEntity) = toggleBugsCaughtUseCase.toggle(bug)

}