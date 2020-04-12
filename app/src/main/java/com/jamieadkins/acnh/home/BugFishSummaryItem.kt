package com.jamieadkins.acnh.home

import android.view.View
import androidx.annotation.StringRes
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_currently_available.view.*
import org.threeten.bp.format.DateTimeFormatter

data class BugFishSummaryItem(
    val bugFishSummary: BugFishSummaryEntity,
    @StringRes val titleText: Int,
    val showTime: Boolean
) : Item(titleText.toLong()) {

    override fun getLayout(): Int = R.layout.view_currently_available

    override fun bind(viewHolder: GroupieViewHolder, position: Int)  = viewHolder.itemView.bind()

    private fun View.bind() {
        title.setText(titleText)
        fishCount.text = String.format("%02d", bugFishSummary.fish.size)
        bugsCount.text = String.format("%02d", bugFishSummary.bugs.size)
        currentTime.text = bugFishSummary.timeEvaluatedAt.format(DateTimeFormatter.ofPattern("hh:mm a"))
        currentTime.visibility = if (showTime) View.VISIBLE else View.GONE
        more.text = resources.getQuantityString(R.plurals.more_uncaught, bugFishSummary.alreadyCaught, bugFishSummary.alreadyCaught)
    }
}