package com.jamieadkins.acnh.predictions

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import coil.api.load
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.MatchEntity
import com.jamieadkins.acnh.domain.MatchOutcomeEntity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_match.view.*

data class PredictionItem(
    val match: MatchEntity
) : Item(match.id) {

    override fun getLayout(): Int = R.layout.view_match

    override fun bind(viewHolder: GroupieViewHolder, position: Int)  = viewHolder.itemView.bind()

    private fun View.bind() {
        homeTeamLogo.load(match.homeTeam.logoUrl)
        awayTeamLogo.load(match.awayTeam.logoUrl)

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        when (match.outcome) {
            MatchOutcomeEntity.HomeTeamWin -> {
                constraintSet.connect(R.id.winnerIndicator, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START,0)
                constraintSet.connect(R.id.winnerIndicator, ConstraintSet.END, R.id.center, ConstraintSet.START, 0)
            }
            MatchOutcomeEntity.AwayTeamWin -> {
                constraintSet.connect(R.id.winnerIndicator, ConstraintSet.START, R.id.center, ConstraintSet.END,0)
                constraintSet.connect(R.id.winnerIndicator, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END,0)
            }
            MatchOutcomeEntity.Tie -> {
                constraintSet.connect(R.id.winnerIndicator, ConstraintSet.END, R.id.center, ConstraintSet.START, 0)
                constraintSet.connect(R.id.winnerIndicator, ConstraintSet.START, R.id.center, ConstraintSet.END,0)
            }
        }
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintLayout)
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int = 1
}