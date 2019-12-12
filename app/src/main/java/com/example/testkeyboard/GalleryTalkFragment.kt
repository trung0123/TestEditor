package com.example.testkeyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_gallery_talk.*

class GalleryTalkFragment : Fragment(), View.OnClickListener {
    var callback: GalleryListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_talk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ll_gallery_talk_choose_image.setOnClickListener(this)
        ll_gallery_talk_take_photo.setOnClickListener(this)
        ll_gallery_talk_estimate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_gallery_talk_choose_image -> {
                callback?.passData("A")
            }
            R.id.ll_gallery_talk_take_photo -> {
                callback?.passData("B")
            }
            R.id.ll_gallery_talk_estimate -> {
                callback?.passData("C")
            }
        }
    }

    fun setListener(callback: GalleryListener) {
        this.callback = callback
    }

    interface GalleryListener {
        fun passData(name: String)
    }
}