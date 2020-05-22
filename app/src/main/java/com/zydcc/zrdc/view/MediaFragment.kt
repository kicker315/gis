package com.zydcc.zrdc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.zydcc.zrdc.R
import com.zydcc.zrdc.adapters.*
import com.zydcc.zrdc.databinding.FragmentMediaBinding
import com.zydcc.zrdc.utilities.PositionUtil

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
        val tabLayout = binding.titleTab
        val viewPager = binding.vp
        viewPager.adapter = MediaPageAdapter(this)
        // set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
        initCallback(binding)


        return binding.root
    }

    private fun initCallback(binding: FragmentMediaBinding) {
        binding.apply {
            callback = object: Callback {

                override fun navToDraft() {
                    val navDirections =  MediaFragmentDirections.actionMediaToDraw()
                    findNavController().navigate(navDirections)
                }

            }
        }
    }

    private fun getTabTitle(position: Int) : String? {
        return when (position) {
            MEDIA_CAMERA_PAGE -> getString(R.string.txt_camera)
            MEDIA_VIDEO_PAGE -> getString(R.string.txt_video)
            MEDIA_RECORD_PAGE -> getString(R.string.txt_record)
            MEDIA_DRAW_PAGE -> getString(R.string.txt_draw_free)
            else -> null
        }
    }


    interface Callback {
        fun navToDraft()
    }


}