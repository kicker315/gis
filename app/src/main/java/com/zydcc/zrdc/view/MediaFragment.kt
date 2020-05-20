package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.zydcc.zrdc.databinding.FragmentMediaBinding

/**
 * =======================================
 * 多媒体界面
 * Create by ningsikai 2020/5/20:8:31 PM
 * ========================================
 */
class MediaFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMediaBinding.inflate(inflater, container, false)
        context ?: return  binding.root
        binding.toolBar.setNavigationOnClickListener {view ->
            view.findNavController().navigateUp()
        }
        return binding.root
    }


}