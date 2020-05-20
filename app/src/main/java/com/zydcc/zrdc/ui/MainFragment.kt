package com.zydcc.zrdc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.zydcc.zrdc.R
import com.zydcc.zrdc.adapters.*
import com.zydcc.zrdc.databinding.FragmentMainBinding

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/18:4:18 PM
 * ========================================
 */
class MainFragment: Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false // 禁止滑动
        viewPager.adapter = MainPageAdapter(this)
        initTab(binding.rgTools, viewPager)
        binding.rbSetting.setOnClickListener {view ->
            val directions =
                MainFragmentDirections.actionMainFragmentToSysFragment()
            view.findNavController().navigate(directions)
        }
        return binding.root
    }

    private fun initTab(rgTools: RadioGroup, viewPager: ViewPager2) {
        rgTools.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_map -> {
                    viewPager.currentItem = MAP_PAGE_INDEX
                }
                R.id.rb_layer_manager -> {
                    viewPager.currentItem = LAYER_PAGE_INDEX
                }
                R.id.rb_query -> {
                    viewPager.currentItem = QUERY_PAGE_INDEX
                }
                R.id.rb_analyst -> {
                    viewPager.currentItem = ANALYST_PAGE_INDEX
                }
            }
        }
    }


}