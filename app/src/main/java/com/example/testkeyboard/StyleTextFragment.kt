package com.example.testkeyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testkeyboard.RichEditor.RichEditor.Type
import com.example.testkeyboard.RichEditor.TextStyle
import kotlinx.android.synthetic.main.fragment_style_text.*
import java.io.Serializable


@Suppress("UNCHECKED_CAST")
class StyleTextFragment : Fragment(), View.OnClickListener {

    companion object {
        val TAG = StyleTextFragment::class.java.simpleName

        fun newInstance(types: MutableList<Type>): StyleTextFragment {
            val fragment = StyleTextFragment()
            val bundle = Bundle()
            bundle.putSerializable("types", types as Serializable)
            fragment.arguments = bundle
            return fragment
        }
    }

    var callback: GalleryListener? = null
    private var isBold = false
    private var isItalic = false
    private var isStrike = false
    private var isUnderline = false
    private lateinit var types: MutableList<Type>

    private var buttons: ArrayList<WriteCustomButton>? = null
    private var textViews: ArrayList<WriteCustomTextView>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.types = it.getSerializable("types") as MutableList<Type>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_style_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ll_style_left.setOnClickListener(this)
        ll_style_center.setOnClickListener(this)
        ll_style_right.setOnClickListener(this)
        ll_text_small.setOnClickListener(this)
        ll_text_normal.setOnClickListener(this)
        ll_text_big.setOnClickListener(this)
        ll_text_biggest.setOnClickListener(this)
        ll_text_bold.setOnClickListener(this)
        ll_text_italic.setOnClickListener(this)
        ll_text_strike.setOnClickListener(this)
        ll_text_underline.setOnClickListener(this)

        setStyleText(types)
    }

    fun setStyleText(listType: MutableList<Type>) {
        types = listType
        if (types.size > 0) {
            buttons = ArrayList(
                listOf<WriteCustomButton>(
                    imb_style_left, imb_style_center, imb_style_right
                )
            )
            textViews = ArrayList(
                listOf<WriteCustomTextView>(
                    tv_style_h4, tv_style_h3, tv_style_h2, tv_style_h1
                )
            )
            for (type in types) {
                when (type.name) {
                    "JUSTIFYLEFT" -> {
                        if (imb_style_left.isChecked) {
                            imb_style_left.switchCheckedState()

                        }
                        ll_style_left.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        buttons!!.remove(imb_style_left)
                    }
                    "JUSTIFYCENTER" -> {
                        if (imb_style_center.isChecked) {
                            imb_style_center.switchCheckedState()

                        }
                        ll_style_center.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        buttons!!.remove(imb_style_center)
                    }
                    "JUSTIFYRIGHT" -> {
                        if (imb_style_right.isChecked) {
                            imb_style_right.switchCheckedState()

                        }
                        ll_style_right.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        buttons!!.remove(imb_style_right)
                    }
                    "H1" -> {
                        if (tv_style_h1.isChecked) {
                            tv_style_h1.switchCheckedState()
                        }
                        ll_text_biggest.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_h1)
                    }
                    "H2" -> {
                        if (tv_style_h2.isChecked) {
                            tv_style_h2.switchCheckedState()
                        }
                        ll_text_big.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_h2)
                    }
                    "H3" -> {
                        if (tv_style_h3.isChecked) {
                            tv_style_h3.switchCheckedState()
                        }
                        ll_text_normal.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_h3)
                    }
                    "H4" -> {
                        if (tv_style_h4.isChecked) {
                            tv_style_h4.switchCheckedState()
                        }
                        ll_text_small.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_h4)
                    }
                }
            }
            for (button in buttons!!) {
                button.isChecked = false
                when (button.id) {
                    R.id.imb_style_left -> ll_style_left.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.imb_style_center -> ll_style_center.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.imb_style_right -> ll_style_right.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                }
            }

            for (textView in textViews!!) {
                textView.isChecked = false
                when (textView.id) {
                    R.id.tv_style_h1 -> ll_text_biggest.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_style_h2 -> ll_text_big.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_style_h3 -> ll_text_normal.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_style_h4 -> ll_text_small.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_style_left -> {
                callback?.passData(TextStyle.TextLeft)
                imb_style_left.switchCheckedState()
            }

            R.id.ll_style_center -> {
                callback?.passData(TextStyle.TextCenter)
                imb_style_center.switchCheckedState()
            }

            R.id.ll_style_right -> {
                callback?.passData(TextStyle.TextRight)
                imb_style_right.switchCheckedState()
            }

            R.id.ll_text_small -> {
                callback?.passData(TextStyle.H4)
                tv_style_h4.switchCheckedState()
            }

            R.id.ll_text_normal -> {
                callback?.passData(TextStyle.H3)
                tv_style_h3.switchCheckedState()
            }

            R.id.ll_text_big -> {
                callback?.passData(TextStyle.H2)
                tv_style_h2.switchCheckedState()
            }

            R.id.ll_text_biggest -> {
                callback?.passData(TextStyle.H1)
                tv_style_h1.switchCheckedState()
            }

            R.id.ll_text_bold -> {
                isBold = !isBold
                callback?.passData(TextStyle.Bold)
                setTextStyleLayout(Style.BOLD, isBold)
                tv_style_bold.switchCheckedState()
            }

            R.id.ll_text_italic -> {
                isItalic = !isItalic
                callback?.passData(TextStyle.Italic)
                setTextStyleLayout(Style.ITALIC, isItalic)
                tv_style_italic.switchCheckedState()
            }

            R.id.ll_text_strike -> {
                isStrike = !isStrike
                callback?.passData(TextStyle.StrikeThrough)
                setTextStyleLayout(Style.STRIKE, isStrike)
                tv_style_strike.switchCheckedState()
            }

            R.id.ll_text_underline -> {
                isUnderline = !isUnderline
                callback?.passData(TextStyle.Underline)
                setTextStyleLayout(Style.UNDERLINE, isUnderline)
                tv_style_underline.switchCheckedState()
            }
        }
    }

    private fun setTextStyleLayout(style: Style, state: Boolean) {
        when (style) {
            Style.BOLD -> {
                if (state) {
                    ll_text_bold.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.black
                        )
                    )
                } else {
                    ll_text_bold.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.white
                        )
                    )
                }
            }
            Style.ITALIC -> {
                if (state) {
                    ll_text_italic.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.black
                        )
                    )
                } else {
                    ll_text_italic.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.white
                        )
                    )
                }
            }
            Style.STRIKE -> {
                if (state) {
                    ll_text_strike.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.black
                        )
                    )
                } else {
                    ll_text_strike.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.white
                        )
                    )
                }
            }
            Style.UNDERLINE -> {
                if (state) {
                    ll_text_underline.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.black
                        )
                    )
                } else {
                    ll_text_underline.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            android.R.color.white
                        )
                    )
                }
            }
        }
    }

    interface GalleryListener {
        fun passData(style: TextStyle)
    }

    enum class Style {
        LEFT,
        RIGHT,
        CENTER,
        SMALL,
        NORMAL,
        BIG,
        BIGGEST,
        BOLD,
        ITALIC,
        STRIKE,
        UNDERLINE
    }
}