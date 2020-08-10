package com.ningsk.zrdc.ui.media.draw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ningsk.zrdc.R


/**
 * =======================================
 *
 * Create by ningsikai 2020/5/21:5:20 PM
 * ========================================
 */
class MediaDrawFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_media_draw, container, false)
    }
}