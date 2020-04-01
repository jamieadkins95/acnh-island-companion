package com.jamieadkins.acnh.home

import androidx.annotation.StringRes
import com.jamieadkins.acnh.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_going_soon.view.*

class SubHeaderWithSubTitleItem(
    @StringRes private val title: Int,
    @StringRes private val subtitle: Int
) : Item(title.toLong()) {

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean = other is SubHeaderWithSubTitleItem

    override fun getLayout(): Int = R.layout.view_going_soon

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.title.setText(title)
        viewHolder.itemView.subtitle.setText(subtitle)
    }
}