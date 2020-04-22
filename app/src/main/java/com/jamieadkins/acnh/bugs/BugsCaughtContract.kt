package com.jamieadkins.acnh.bugs

import com.jamieadkins.acnh.domain.bugs.BugEntity

interface BugsCaughtContract {

    fun onBugCaughtToggled(bug: BugEntity)
}