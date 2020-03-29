package com.jamieadkins.acnh.bugs

import android.view.View
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.extensions.getMonthRange
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_bug.view.*

data class BugItem(val bug: BugEntity) : Item(bug.id.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.view_bug

    override fun bind(viewHolder: GroupieViewHolder, position: Int)  = viewHolder.itemView.bind()

    private fun View.bind() {
        name.text = bug.name
        price.text = bug.price
        time.text = bug.timeRange
        months.text = resources.getMonthRange(bug.months)
    }
}