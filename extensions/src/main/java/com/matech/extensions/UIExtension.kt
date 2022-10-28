package com.matech.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.clickable(enabled: Boolean) {
    isClickable = enabled
    alpha = if (enabled) 1f else 0.75f
}


fun RecyclerView.verticalDecoration(
    context: Context,
    drawable: Int? = R.drawable.recycler_divider
) {

    val item = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    ContextCompat.getDrawable(context, drawable!!)?.let {
        item.setDrawable(
            it
        )
    }
    addItemDecoration(item)
}

fun RecyclerView.horizontalDecoration(
    drawable: Int? = R.drawable.recycler_divider
) {

    val item = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
    ContextCompat.getDrawable(context, drawable!!)?.let {
        item.setDrawable(
            it
        )
    }
    addItemDecoration(item)
}

fun RecyclerView.horizontalLayout() {
    layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
}
fun RecyclerView.horizontalReverseLayout() {
    layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, true)
}

fun RecyclerView.verticalLayout() {
    layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
}
fun RecyclerView.verticalReverseLayout() {
    layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, true)
}

fun RecyclerView.gridLayout(spanCount: Int) {
    layoutManager = GridLayoutManager(this.context, spanCount)
}


fun MaterialTextView.setGradientTextColor() {
    val paint = this.paint
    val width = paint.measureText(this.text.toString())
    val textShader: Shader = LinearGradient(
        0f, 0f, 0f, this.textSize, intArrayOf(
            Color.parseColor("#2FCF00"),
            Color.parseColor("#FF1F7723")
        ), null, Shader.TileMode.CLAMP
    )

    this.paint.shader = textShader
}
