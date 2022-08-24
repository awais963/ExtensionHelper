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


fun View.setOnSingleClickListener(debounceTime: Long = 4000L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            val timeNow = SystemClock.elapsedRealtime()
            val elapsedTimeSinceLastClick = timeNow - lastClickTime
            if (elapsedTimeSinceLastClick < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun Fragment.navigateTo(directions: NavDirections) {
    val controller = findNavController()
    val currentDestination =
        (controller.currentDestination as? FragmentNavigator.Destination)?.className
            ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
    if (currentDestination == this.javaClass.name) {
        controller.navigate(directions)
    }
}

fun Fragment.navigate(destination: NavDirections) = with(findNavController()) {
    currentDestination?.getAction(destination.actionId)
        ?.let { navigate(destination) }
}

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
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
    context: Context,
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

fun RecyclerView.verticalLayout() {
    layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
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

fun toastMessage(context: Context?, message: String?) {
    if (!message.isNullOrEmpty()) {
        val toast = makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}
