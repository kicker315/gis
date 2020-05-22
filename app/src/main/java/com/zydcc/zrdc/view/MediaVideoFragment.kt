package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentMediaVideoBinding

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/21:5:21 PM
 * ========================================
 */
class MediaVideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMediaVideoBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }
}