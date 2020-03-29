package com.jamieadkins.acnh.home

import android.view.View
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.AvailableNowEntity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_currently_available.view.*
import org.threeten.bp.format.DateTimeFormatter

data class CurrentlyAvailableItem(val availableNow: AvailableNowEntity) : Item(R.string.currently_available.toLong()) {

    override fun getLayout(): Int = R.layout.view_currently_available

    override fun bind(viewHolder: GroupieViewHolder, position: Int)  = viewHolder.itemView.bind()

    private fun View.bind() {
        fishCount.text = availableNow.fish.size.toString()
        bugsCount.text = availableNow.bugs.size.toString()
        currentTime.text = availableNow.timeEvaluatedAt.format(DateTimeFormatter.ofPattern("hh:mm a"))
    }
}