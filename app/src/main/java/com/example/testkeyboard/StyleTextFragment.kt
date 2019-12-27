package com.example.testkeyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testkeyboard.RichEditor.RichEditor.Type
import com.example.testkeyboard.Utils.TextStyle
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
                    tv_small_text, tv_normal_text, tv_big_text, tv_biggest_test,
                    tv_style_bold, tv_style_italic, tv_style_strike, tv_style_underline
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
                    "FONTSIZE_6" -> {
                        if (tv_biggest_test.isChecked) {
                            tv_biggest_test.switchCheckedState()
                        }
                        ll_text_biggest.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_biggest_test)
                    }
                    "FONTSIZE_5" -> {
                        if (tv_big_text.isChecked) {
                            tv_big_text.switchCheckedState()
                        }
                        ll_text_big.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_big_text)
                    }
                    "FONTSIZE_4" -> {
                        if (tv_normal_text.isChecked) {
                            tv_normal_text.switchCheckedState()
                        }
                        ll_text_normal.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_normal_text)
                    }
                    "FONTSIZE_3" -> {
                        if (tv_small_text.isChecked) {
                            tv_small_text.switchCheckedState()
                        }
                        ll_text_small.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_small_text)
                    }
                    "BOLD" -> {
                        if (tv_style_bold.isChecked) {
                            tv_style_bold.switchCheckedState()
                        }
                        ll_text_bold.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_bold)
                    }
                    "ITALIC" -> {
                        if (tv_style_italic.isChecked) {
                            tv_style_italic.switchCheckedState()
                        }
                        ll_text_italic.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_italic)
                    }
                    "STRIKETHROUGH" -> {
                        if (tv_style_strike.isChecked) {
                            tv_style_strike.switchCheckedState()
                        }
                        ll_text_strike.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_strike)
                    }
                    "UNDERLINE" -> {
                        if (tv_style_underline.isChecked) {
                            tv_style_underline.switchCheckedState()
                        }
                        ll_text_underline.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.black
                            )
                        )
                        textViews!!.remove(tv_style_underline)
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
                    R.id.tv_biggest_test -> ll_text_biggest.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_big_text -> ll_text_big.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_normal_text -> ll_text_normal.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_small_text -> ll_text_small.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_style_bold -> ll_text_bold.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_style_italic -> ll_text_italic.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_style_strike -> ll_text_strike.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    R.id.tv_style_underline -> ll_text_underline.setBackgroundColor(
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
                callback?.passData(TextStyle.Small)
                tv_small_text.switchCheckedState()
            }

            R.id.ll_text_normal -> {
                callback?.passData(TextStyle.Normal)
                tv_normal_text.switchCheckedState()
            }

            R.id.ll_text_big -> {
                callback?.passData(TextStyle.Big)
                tv_big_text.switchCheckedState()
            }

            R.id.ll_text_biggest -> {
                callback?.passData(TextStyle.Biggest)
                tv_biggest_test.switchCheckedState()
            }

            R.id.ll_text_bold -> {
                callback?.passData(TextStyle.Bold)
                tv_style_bold.switchCheckedState()
            }

            R.id.ll_text_italic -> {
                callback?.passData(TextStyle.Italic)
                tv_style_italic.switchCheckedState()
            }

            R.id.ll_text_strike -> {
                callback?.passData(TextStyle.StrikeThrough)
                tv_style_strike.switchCheckedState()
            }

            R.id.ll_text_underline -> {
                callback?.passData(TextStyle.Underline)
                tv_style_underline.switchCheckedState()
            }
        }
    }

    interface GalleryListener {
        fun passData(style: TextStyle)
    }
}