package com.example.testkeyboard.Utils

import com.example.testkeyboard.R
import java.util.regex.Pattern

val colorMap: LinkedHashMap<Int, Int> = object : LinkedHashMap<Int, Int>() {
    init {
        put(R.id.color_white, R.color.white)
        put(R.id.color_black, R.color.black)
        put(R.id.color_maroon, R.color.maroon)
        put(R.id.color_red, R.color.red)
        put(R.id.color_lime, R.color.lime)
        put(R.id.color_magenta, R.color.magenta)
        put(R.id.color_pink, R.color.pink)
        put(R.id.color_orange, R.color.orange)
        put(R.id.color_yellow, R.color.yellow)
        put(R.id.color_aqua, R.color.aqua)
        put(R.id.color_blue, R.color.blue)
        put(R.id.color_sky_blue, R.color.sky_blue)
        put(R.id.color_pale_cyan, R.color.pale_cyan)
        put(R.id.color_green, R.color.green)
    }
}

fun getColor(color: String): Int {
    val mColor = color.toLowerCase()
    val regex = "[a-zA-Z]+_[a-zA-Z]+_(\\w+)"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(mColor)
    val colorName: String
    return if (matcher.find()) {
        colorName = matcher.group(1)!!
        when (colorName) {
            "black" -> R.color.black
            "maroon" -> R.color.maroon
            "red" -> R.color.red
            "magenta" -> R.color.magenta
            "pink" -> R.color.pink
            "orange" -> R.color.orange
            "yellow" -> R.color.yellow
            "lime" -> R.color.lime
            "aqua" -> R.color.aqua
            "blue" -> R.color.blue
            "sky_blue" -> R.color.sky_blue
            "pale_cyan" -> R.color.pale_cyan
            "green" -> R.color.green
            else -> R.color.black
        }
    } else {
        R.color.black
    }
}