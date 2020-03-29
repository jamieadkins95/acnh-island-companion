package com.jamieadkins.acnh

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.extensions.setMargins
import com.jamieadkins.acnh.extensions.setMarginsToGrid
import com.xwray.groupie.GroupAdapter

class CritterListDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val item = (parent.adapter as GroupAdapter).getItem(position)

        val defaultSpace = view.resources.getDimension(R.dimen.default_margin).toInt()

        view.setMargins(top = defaultSpace)
        val layoutManager = parent.layoutManager
        if (layoutManager is androidx.recyclerview.widget.GridLayoutManager) {
            view.setMarginsToGrid(layoutManager, defaultSpace)
        } else {
            view.setMargins(left = defaultSpace, right = defaultSpace)
        }
    }
}