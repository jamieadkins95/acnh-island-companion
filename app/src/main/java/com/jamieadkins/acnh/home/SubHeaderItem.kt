package com.jamieadkins.acnh.home

import android.view.View
import androidx.annotation.StringRes
import com.jamieadkins.acnh.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.view_subheader.view.*
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

data class SubHeaderItem(@StringRes private val text: Int, private val time: ZonedDateTime) : Item(text.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.view_subheader

    override fun bind(viewHolder: GroupieViewHolder, position: Int)  = viewHolder.itemView.bind()

    private fun View.bind() {
        title.setText(text)
        currentTime.text = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
    }
}