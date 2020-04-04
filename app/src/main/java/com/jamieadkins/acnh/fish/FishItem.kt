package com.jamieadkins.acnh.fish

import android.view.View
import coil.api.load
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.extensions.getMonthRange
import com.jamieadkins.acnh.extensions.isConsecutive
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
        months.text = when {
            fish.months.size == 12 -> resources.getString(R.string.all_year)
            fish.months.isConsecutive() -> resources.getMonthRange(fish.months)
            else -> {
                val monthArray = resources.getStringArray(R.array.months)
                fish.months.joinToString { monthArray[it - 1] }
            }
        }
        size.text = fish.size
        location.text = fish.location
        image.load(fish.imageUrl)
    }
}