package com.zydcc.zrdc.ui.layer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.databinding.FragmentLayerBinding

/**
 * =======================================
 * Layer
 * Create by ningsikai 2020/5/19:1:42 PM
 * ========================================
 */
class LayerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLayerBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }


}