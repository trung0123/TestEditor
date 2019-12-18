package com.example.testkeyboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.testkeyboard.Utils.colorMap

/**
 * A simple [Fragment] subclass.
 */
class ColorFragment : Fragment() {
    var callback: ColorListener? = null
    private var type: Int = 0

    companion object {
        val TAG = ColorFragment::class.java.simpleName

        fun newInstance(type: Int): ColorFragment {
            val fragment = ColorFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.type = it.getInt("type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (key in colorMap.keys) {
            val value = colorMap[key]
            val button = view.findViewById<Button>(key)
            button.setOnClickListener {
                if (type == 1)
                    callback?.passTextColor(value!!)
                else
                    callback?.passBackgroundColor(value!!)
            }
        }

    }

    interface ColorListener {
        fun passTextColor(value: Int)
        fun passBackgroundColor(value: Int)
    }


}
