package com.jamieadkins.acnh.bugs

import android.view.View
import coil.api.load
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.extensions.getMonthRange
import com.jamieadkins.acnh.extensions.isConsecutive
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_bug.view.*
data class BugItem(
    val bug: BugEntity,
    val onCaughtToggled: (BugEntity) -> Unit
) : Item(bug.id.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.view_bug

    override fun bind(viewHolder: GroupieViewHolder, position: Int) = viewHolder.itemView.bind()

    private fun View.bind() {
        name.text = bug.name
        price.text = bug.price.toIntOrNull()?.let { String.format("%,d", it) } ?: bug.price
        time.text = bug.timeRange
        months.text = when {
            bug.months.size == 12 -> resources.getString(R.string.all_year)
            bug.months.isConsecutive() -> resources.getMonthRange(bug.months)
            else -> {
                val monthArray = resources.getStringArray(R.array.months)
                bug.months.joinToString { monthArray[it - 1] }
            }
        }
        location.text = bug.location
        image.load(bug.imageUrl)
        caught.text = if (bug.caught) resources.getString(R.string.caught) else resources.getString(R.string.uncaught)
        caught.setOnCheckedChangeListener(null)
        caught.isChecked = bug.caught
        caught.setOnCheckedChangeListener { _, isChecked -> onCaughtToggled(bug) }
    }
}