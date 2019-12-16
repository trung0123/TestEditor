package com.example.testkeyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testkeyboard.RichEditor.TextStyle
import kotlinx.android.synthetic.main.fragment_style_text.*


class StyleTextFragment : Fragment(), View.OnClickListener {
    var callback: GalleryListener? = null
    private var isBold = false
    private var isItalic = false
    private var isStrike = false
    private var isUnderline = false

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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_style_left -> {
                callback?.passData(TextStyle.TextLeft)
                setStyleLayout(Style.LEFT)
                imb_style_left.switchCheckedState()
            }

            R.id.ll_style_center -> {
                callback?.passData(TextStyle.TextCenter)
                setStyleLayout(Style.CENTER)
                imb_style_center.switchCheckedState()
            }

            R.id.ll_style_right -> {
                callback?.passData(TextStyle.TextRight)
                setStyleLayout(Style.RIGHT)
                imb_style_right.switchCheckedState()
            }

            R.id.ll_text_small -> {
                callback?.passData(TextStyle.H4)
                setTextSizeLayout(Style.SMALL)
            }

            R.id.ll_text_normal -> {
                callback?.passData(TextStyle.H3)
                setTextSizeLayout(Style.NORMAL)
            }

            R.id.ll_text_big -> {
                callback?.passData(TextStyle.H2)
                setTextSizeLayout(Style.BIG)
            }

            R.id.ll_text_biggest -> {
                callback?.passData(TextStyle.H1)
                setTextSizeLayout(Style.BIGGEST)
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

    private fun setStyleLayout(style: Style) {
        ll_style_left.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_style_center.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_style_right.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_text_small.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_text_normal.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_text_big.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_text_biggest.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )

        ll_text_bold.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    android.R.color.white
                )
            )
            isBold = false
        }

        ll_text_italic.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    android.R.color.white
                )
            )
            isItalic = false
        }

        ll_text_strike.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    android.R.color.white
                )
            )
            isStrike = false
        }

        ll_text_underline.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    android.R.color.white
                )
            )
            isUnderline = false
        }

        when (style) {
            Style.LEFT -> {
                ll_style_left.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
                ll_text_normal.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
            }
            Style.CENTER -> {
                ll_style_center.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
                ll_text_normal.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
            }
            Style.RIGHT -> {
                ll_style_right.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
                ll_text_normal.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
            }
        }
    }

    private fun setTextSizeLayout(style: Style) {
        ll_text_small.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_text_normal.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_text_big.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        ll_text_biggest.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.white
            )
        )
        when (style) {
            Style.SMALL -> {
                ll_text_small.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
            }
            Style.NORMAL -> {
                ll_text_normal.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
            }
            Style.BIG -> {
                ll_text_big.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
            }
            Style.BIGGEST -> {
                ll_text_biggest.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        android.R.color.black
                    )
                )
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