package com.example.testkeyboard

import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.testkeyboard.RichEditor.*
import com.rockerhieu.emojicon.EmojiconsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs


class MainActivity : AppCompatActivity(),
    OnKeyboardVisibilityListener, View.OnClickListener,
    StyleTextFragment.GalleryListener {

    var globalLayoutListener: OnGlobalLayoutListener? = null
    var isShown = false
    var hideKeyboard: Int = 0
    var showKeyboard: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEvents()
        initEditor()
        // Set Keyboard Event
        setKeyboardVisibilityListener(this)
    }

    private fun initEditor() {
        edt_chat.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        edt_chat.setEditorFontSize(22)
        edt_chat.setEditorFontColor(Color.BLACK)
        edt_chat.setPadding(10, 10, 10, 10)
        edt_chat.isFocusable = true
        edt_chat.setPlaceholder("Insert text here...")
    }

    private fun setEvents() {
        ll_text_style.setOnClickListener(this)
        ll_smile.setOnClickListener(this)
        ll_keyboard.setOnClickListener(this)
        ll_camera.setOnClickListener(this)
        ll_text_color.setOnClickListener(this)
        ll_background_color.setOnClickListener(this)
        ll_insert_link.setOnClickListener(this)

        edt_chat.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                ll_keyboard.tag = "0"
                edt_chat.tag = "1"
                if (isSmilesLayoutShowing()) {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                } else {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
                if (!isShown) {
                    showKeyboard(this)
                }
            }

            false
        }

        edt_chat.setOnTextChangeListener { text ->
            Log.d("Trung", text)
        }
    }

    private fun setKeyboardVisibilityListener(onKeyboardVisibilityListener: OnKeyboardVisibilityListener) {
        if (globalLayoutListener == null) {
            globalLayoutListener = object : OnGlobalLayoutListener {
                private val defaultKeyboardHeightDP = 100
                private val EstimatedKeyboardDP =
                    defaultKeyboardHeightDP + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) 48 else 0
                private val rect = Rect()
                private var alreadyOpen = false
                override fun onGlobalLayout() {
                    val estimatedKeyboardHeight = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        EstimatedKeyboardDP.toFloat(), ll_talk_main.resources.displayMetrics
                    ).toInt()
                    ll_talk_main.getWindowVisibleDisplayFrame(rect)
                    val heightDiff: Int = ll_talk_main.rootView.height - (rect.bottom - rect.top)
                    Log.d("heightDiff", heightDiff.toString())
                    isShown = heightDiff >= estimatedKeyboardHeight
                    if (heightDiff <= 0) {
                        return
                    }
                    if (isShown == alreadyOpen) {
                        Log.i("Keyboard state", "Ignoring global layout change...")
                        if (convertPxToDp(heightDiff) > 0) {
                            hideKeyboard = convertPxToDp(heightDiff)
                        }
                        return
                    }
                    alreadyOpen = isShown
                    if (convertPxToDp(heightDiff) > 100) {
                        showKeyboard =
                            convertPxToDp(heightDiff)
                        val params = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            convertDpToPx(abs(showKeyboard - hideKeyboard))
                        )
                        frame_bottom.layoutParams = params
                    }
                    onKeyboardVisibilityListener.onVisibilityChanged(isShown)
                }
            }
            ll_talk_main.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        }
    }

    override fun onVisibilityChanged(visible: Boolean) {
        if (!visible && (ll_keyboard.tag == "1" || edt_chat.tag == "1")) {
            if (isSmilesLayoutShowing()) {
                hideGalleryLayout()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_keyboard -> {
                ll_keyboard.tag = "1"
                edt_chat.tag = "0"
                if (isSmilesLayoutShowing()) {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                } else {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
                if (isShown) {
                    hideKeyboard(this)
                    if (isSmilesLayoutShowing()) {
                        hideGalleryLayout()
                    }
                } else {
                    showKeyboard(this)
                }
            }
            R.id.ll_text_style -> {
                ll_keyboard.tag = "0"
                edt_chat.tag = "0"
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                onReplaceFragmentGalleryTalk()
                showGalleryLayout()
                if (isShown) {
                    hideKeyboard(this)
                }

            }
            R.id.ll_smile -> {
                ll_keyboard.tag = "0"
                edt_chat.tag = "0"
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                onReplaceFragmentEmoji()
                showGalleryLayout()
                if (isShown) {
                    hideKeyboard(this)
                }
            }
        }
    }

    private fun onReplaceFragmentGalleryTalk() {
        val styleTextFragment = StyleTextFragment()
        styleTextFragment.callback = this
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_bottom, styleTextFragment, "StyleTextFragment").commit()
    }

    private fun showGalleryLayout() {
        frame_bottom.visibility = View.VISIBLE
    }

    private fun onReplaceFragmentEmoji() {
        val fragment =
            supportFragmentManager.findFragmentByTag("emojiconsFragment") as EmojiconsFragment?
        if (fragment == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.frame_bottom,
                    EmojiconsFragment.newInstance(false),
                    "emojiconsFragment"
                ).commit()
        }
    }

    private fun hideGalleryLayout() {
        frame_bottom.visibility = View.GONE
    }

    private fun isSmilesLayoutShowing(): Boolean {
        return frame_bottom.visibility == View.VISIBLE
    }

    override fun passData(style: TextStyle) {
        when (style) {
            TextStyle.H1 -> edt_chat.setHeading(1)
            TextStyle.H2 -> edt_chat.setHeading(2)
            TextStyle.H3 -> edt_chat.setHeading(3)
            TextStyle.H4 -> edt_chat.setHeading(4)
            TextStyle.TextLeft -> {
                edt_chat.clearFocusEditor()
                edt_chat.setAlignLeft()
            }
            TextStyle.TextCenter -> {
                edt_chat.clearFocusEditor()
                edt_chat.setAlignCenter()
            }
            TextStyle.TextRight -> {
                edt_chat.clearFocusEditor()
                edt_chat.setAlignRight()
            }
            TextStyle.Bold -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.clearAndFocusEditor()
                edt_chat.setBold()
            }
            TextStyle.Italic -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.clearAndFocusEditor()
                edt_chat.setItalic()
            }
            TextStyle.StrikeThrough -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.clearAndFocusEditor()
                edt_chat.setStrikeThrough()
            }
            TextStyle.Underline -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.clearAndFocusEditor()
                edt_chat.setUnderline()
            }
        }
    }

    override fun onBackPressed() {
        if (!isSmilesLayoutShowing() && !isShown) {
            super.onBackPressed()
        }

        if (isSmilesLayoutShowing()) {
            hideGalleryLayout()
        }
        if (isShown) {
            hideKeyboard(this)
        }
    }
}
