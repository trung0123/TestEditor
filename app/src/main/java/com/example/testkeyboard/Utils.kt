package com.example.testkeyboard

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager

interface OnKeyboardVisibilityListener {
    fun onVisibilityChanged(visible: Boolean)
}

fun convertPxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun convertDpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun hideKeyboard(activity: Activity) {
    val inputManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (activity.currentFocus != null) {
        inputManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun showKeyboard(activity: Activity) {
    val inputManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
}

sealed class TextStyle {
    object H1 : TextStyle()
    object H2 : TextStyle()
    object H3 : TextStyle()
    object H4 : TextStyle()
    object TextLeft : TextStyle()
    object TextCenter : TextStyle()
    object TextRight : TextStyle()
    object Bold : TextStyle()
    object Italic : TextStyle()
    object StrikeThrough : TextStyle()
    object Underline : TextStyle()

}