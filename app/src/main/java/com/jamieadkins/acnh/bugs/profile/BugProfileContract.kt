package com.jamieadkins.acnh.bugs.profile

import com.jamieadkins.acnh.domain.bugs.BugEntity

interface BugProfileContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showBug(bug: BugEntity)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun init(bugId: String)
        fun onDetach()
    }
}