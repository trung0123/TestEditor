package com.example.testkeyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class WriteCustomButton(context: Context?, attrs: AttributeSet?) :
    ImageView(context, attrs) {

    var isChecked = false

    fun switchCheckedState() {
        isChecked = !isChecked
    }

}