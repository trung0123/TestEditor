package com.example.testkeyboard

import android.content.Intent
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
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.example.testkeyboard.RichEditor.RichEditor.OnDecorationStateListener
import com.example.testkeyboard.RichEditor.RichEditor.Type
import com.example.testkeyboard.Utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs


class MainActivity : AppCompatActivity(),
    OnKeyboardVisibilityListener, View.OnClickListener,
    StyleTextFragment.GalleryListener, ColorFragment.ColorListener {

    private var globalLayoutListener: OnGlobalLayoutListener? = null
    private var isShown = false
    private var hideKeyboard: Int = 0
    private var showKeyboard: Int = 0
    private var buttons: ArrayList<WriteCustomButton>? = null
    private var listType: MutableList<Type>? = null
    private lateinit var onDecorationChange: OnDecorationStateListener
    private var heightDiffTemp: Int = 0


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
        edt_chat.setEditorFontColor(Color.BLACK)
        edt_chat.setFontSize(3)
        edt_chat.setEditorPadding(10, 10, 10, 10)
        edt_chat.setAlignLeft()

        edt_chat.setOnInitialLoadListener { isReady ->
            if (isReady) {
                edt_chat.focusEditor()
                showKeyboard(this)
            }
        }

        edt_chat.setPlaceholder("Insert text here...")

        onDecorationChange =
            OnDecorationStateListener { _, types ->
                buttons = ArrayList(
                    listOf<WriteCustomButton>(
                        imb_background_color,
                        imb_text_color
                    )
                )
                listType = types
                checkStyleTextFragment()?.setStyleText(listType!!)

                for (type in types) {
                    when {
                        type.name.contains("FONT_COLOR") -> {
                            imb_text_color.setColorFilter(
                                ContextCompat.getColor(
                                    this,
                                    getColor(type.name)
                                )
                            )
                            if (imb_text_color.isChecked) {
                                imb_text_color.switchCheckedState()
                            }
                            buttons!!.remove(imb_text_color)
                        }
                        type.name.contains("BACKGROUND_COLOR") -> {
                            imb_background_color.setColorFilter(
                                ContextCompat.getColor(
                                    this,
                                    getColor(type.name)
                                )
                            )
                            if (imb_background_color.isChecked) {
                                imb_background_color.switchCheckedState()
                            }
                            buttons!!.remove(imb_background_color)
                        }
                    }
                }

                for (button in buttons!!) {
                    button.setColorFilter(ContextCompat.getColor(this, R.color.black))
                    button.isChecked = false
                }
            }
        edt_chat.setOnDecorationChangeListener(onDecorationChange)
    }

    private fun setEvents() {
        ll_text_style.setOnClickListener(this)
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
                    val heightDiff = ll_talk_main.rootView.height - (rect.bottom - rect.top)
                    Log.d("heightDiff", heightDiff.toString())
                    isShown = heightDiff >= estimatedKeyboardHeight
                    if (heightDiff <= 0) {
                        return
                    }
                    if (isShown == alreadyOpen) {
                        Log.i("Keyboard state", "Ignoring global layout change...")
                        if (!isShown && convertPxToDp(heightDiff) > 0) {
                            hideKeyboard = convertPxToDp(heightDiff)
                        }
                        if (isShown && heightDiff > heightDiffTemp) {
                            heightDiffTemp = heightDiff
                            if (convertPxToDp(heightDiff) > 100) {
                                showKeyboard =
                                    convertPxToDp(heightDiff)
                                val params = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    convertDpToPx(abs(showKeyboard - hideKeyboard))
                                )
                                frame_bottom.layoutParams = params
                            }
                        }
                        return
                    }
                    heightDiffTemp = heightDiff
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
                    edt_chat.focusEditor()
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
            R.id.ll_text_color -> {
                ll_keyboard.tag = "0"
                edt_chat.tag = "0"
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                onReplaceFragmentColor(1)
                showGalleryLayout()
                if (isShown) {
                    hideKeyboard(this)
                }
            }
            R.id.ll_background_color -> {
                ll_keyboard.tag = "0"
                edt_chat.tag = "0"
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                onReplaceFragmentColor(2)
                showGalleryLayout()
                if (isShown) {
                    hideKeyboard(this)
                }
            }
            R.id.ll_camera -> start(v)
            R.id.ll_insert_link -> {
                val builder = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val view = inflater.inflate(R.layout.dialog_link, null)

                val edtTitle: EditText = view.findViewById(R.id.dialog_title)
                val edtLink: EditText = view.findViewById(R.id.dialog_href)
                builder.run {
                    setView(view)
                    setPositiveButton("OK") { _, _ ->
                        edt_chat.insertLink(
                            edtLink.text.toString(),
                            edtTitle.text.toString()
                        )
                    }
                    setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                    create().show()
                }
            }
        }
    }

    private fun onReplaceFragmentGalleryTalk() {
        var styleTextFragment =
            supportFragmentManager.findFragmentByTag(StyleTextFragment.TAG)
        if (styleTextFragment == null) {
            styleTextFragment = StyleTextFragment.newInstance(listType!!)
            styleTextFragment.callback = this
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_bottom, styleTextFragment, StyleTextFragment.TAG).commit()
        } else if (!isSmilesLayoutShowing()) {
            showGalleryLayout()
        }
    }

    private fun checkStyleTextFragment(): StyleTextFragment? {
        return supportFragmentManager.findFragmentByTag(StyleTextFragment.TAG) as StyleTextFragment?
    }

    private fun onReplaceFragmentColor(type: Int) {
        var colorFragment = supportFragmentManager.findFragmentByTag(ColorFragment.TAG + type)
        if (colorFragment == null) {
            colorFragment = ColorFragment.newInstance(type)
            colorFragment.callback = this
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_bottom, colorFragment, ColorFragment.TAG + type).commit()
        } else if (!isSmilesLayoutShowing()) {
            showGalleryLayout()
        }
    }

    private fun showGalleryLayout() {
        frame_bottom.visibility = View.VISIBLE
    }

    private fun hideGalleryLayout() {
        frame_bottom.visibility = View.GONE
    }

    private fun isSmilesLayoutShowing(): Boolean {
        return frame_bottom.visibility == View.VISIBLE
    }

    override fun passData(style: TextStyle) {
        when (style) {
            TextStyle.Small -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.setFontSize(3)
                edt_chat.clearAndFocusEditor()
            }
            TextStyle.Normal -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.setFontSize(4)
                edt_chat.clearAndFocusEditor()
            }
            TextStyle.Big -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.setFontSize(5)
                edt_chat.clearAndFocusEditor()
            }
            TextStyle.Biggest -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.setFontSize(6)
                edt_chat.clearAndFocusEditor()
            }
            TextStyle.TextLeft -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.setAlignLeft()
//                edt_chat.clearFocusEditor()
            }
            TextStyle.TextCenter -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.setAlignCenter()
//                edt_chat.clearFocusEditor()
            }
            TextStyle.TextRight -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

//                edt_chat.clearFocusEditor()
                edt_chat.setAlignRight()
            }
            TextStyle.Bold -> {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                edt_chat.setBold()
                edt_chat.clearAndFocusEditor()
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

    override fun passTextColor(value: Int) {
        edt_chat.setTextColor(ContextCompat.getColor(this, value))
        if (value != R.color.white) imb_text_color.setColorFilter(
            ContextCompat.getColor(this, value)
        ) else imb_text_color.setColorFilter(
            ContextCompat.getColor(this, R.color.black)
        )

        imb_text_color.switchCheckedState()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        showKeyboard(this)
    }

    override fun passBackgroundColor(value: Int) {
        edt_chat.setTextBackgroundColor(ContextCompat.getColor(this, value))
        if (value != R.color.white) imb_background_color.setColorFilter(
            ContextCompat.getColor(this, value)
        ) else imb_background_color.setColorFilter(
            ContextCompat.getColor(this, R.color.black)
        )
        imb_background_color.switchCheckedState()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        showKeyboard(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val images = ImagePicker.getImages(data)
            insertImages(images)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun insertImages(images: List<Image>?) {
        if (images == null) return
        val stringBuffer = StringBuilder()
        var i = 0
        val l = images.size
        while (i < l) {
            stringBuffer.append(images[i].path).append("\n")
            // Handle this
            edt_chat.insertImage("file://" + images[i].path, "alt")
            i++
        }
    }
}
