package com.jamieadkins.acnh.extensions

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.setMargins(left ?: lp.leftMargin, top ?: lp.topMargin, right ?: lp.rightMargin, bottom ?: lp.bottomMargin)
    layoutParams = lp
}

fun View.setMarginsToGrid(gridLayoutManager: GridLayoutManager, margin: Int) {
    val layoutParams = layoutParams as GridLayoutManager.LayoutParams
    val spanSize = layoutParams.spanSize.toFloat()
    val totalSpanSize = gridLayoutManager.spanCount.toFloat()

    val numColumns = totalSpanSize / spanSize // num columns
    val columnIndex = layoutParams.spanIndex / spanSize // column index

    setMargins(left = (margin * ((numColumns - columnIndex) / numColumns)).toInt())
    setMargins(right = (margin * ((columnIndex + 1) / numColumns)).toInt())
}