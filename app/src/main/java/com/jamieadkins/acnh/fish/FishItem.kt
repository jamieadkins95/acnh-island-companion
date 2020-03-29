package com.jamieadkins.acnh.fish

import android.view.View
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.extensions.getMonthRange
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_fish.view.*

data class FishItem(val fish: FishEntity) : Item(fish.id.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.view_fish

    override fun bind(viewHolder: GroupieViewHolder, position: Int)  = viewHolder.itemView.bind()

    private fun View.bind() {
        name.text = fish.name
        price.text = fish.price
        time.text = fish.timeRange
        months.text = resources.getMonthRange(fish.months)
        size.text = fish.size
    }
}