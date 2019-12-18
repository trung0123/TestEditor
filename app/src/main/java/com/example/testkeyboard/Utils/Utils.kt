package com.example.testkeyboard.RichEditor

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.esafirm.imagepicker.features.ImagePicker
import java.io.ByteArrayOutputStream

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
    inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun closeKeyboard(view: View) {
    val imm =
        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun showKeyboard(view: View) {
    val imm =
        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun showSoftKeyboard(dialog: Dialog) {
    dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
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

fun toBase64(bitmap: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val bytes = baos.toByteArray()
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}

fun toBitmap(drawable: Drawable): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    var width = drawable.intrinsicWidth
    width = if (width > 0) width else 1
    var height = drawable.intrinsicHeight
    height = if (height > 0) height else 1
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun decodeResource(context: Context, resId: Int): Bitmap? {
    return BitmapFactory.decodeResource(context.resources, resId)
}

fun getCurrentTime(): Long {
    return System.currentTimeMillis()
}

fun getImagePicker(view: View): ImagePicker {
    val imagePicker = ImagePicker.create(view.context as Activity)
    return imagePicker.limit(10) // max images can be selected (99 by default)
        .toolbarFolderTitle("Gallery")
        .toolbarDoneButtonText("Confirm")
        .showCamera(false) // show camera or not (true by default)
        .folderMode(true)
        .includeVideo(false)
        .imageFullDirectory(view.context.getExternalFilesDir(null)?.absolutePath) // can be full path
}

fun start(view: View) {
    getImagePicker(view).start() // start image picker activity with request code
}