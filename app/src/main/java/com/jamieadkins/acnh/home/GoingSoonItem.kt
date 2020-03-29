package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class GoingSoonItem : Item(R.string.going_away_soon.toLong()) {

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean = other is GoingSoonItem

    override fun getLayout(): Int = R.layout.view_going_soon

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // do nothing.
    }
}