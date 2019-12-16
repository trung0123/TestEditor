package com.example.testkeyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class WriteCustomTextView(context: Context?, attrs: AttributeSet?) :
    TextView(context, attrs) {
    var isChecked = false

    fun switchCheckedState() {
        isChecked = !isChecked
    }
}