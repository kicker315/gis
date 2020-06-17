package com.zydcc.zrdc.ui.view.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentMediaRecordBinding

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/21:5:20 PM
 * ========================================
 */
class MediaRecordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMediaRecordBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }
}