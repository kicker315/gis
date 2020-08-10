package com.ningsk.zrdc.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ningsk.zrdc.R
import com.ningsk.zrdc.event.Location2Map
import kotlinx.android.synthetic.main.fragment_main.*
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber

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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun initData() {
        view_pager.isUserInputEnabled = false // 禁止滑动
        view_pager.adapter = MainPageAdapter(this)
        view_pager.offscreenPageLimit = 4
        initTab(rg_tools, view_pager)
        rb_setting.setOnClickListener {view ->
            val directions =
                MainFragmentDirections.actionMainFragmentToSysFragment()
            view.findNavController().navigate(directions)
        }
    }

    private fun initTab(rgTools: RadioGroup, viewPager: ViewPager2) {
        rgTools.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_map -> {
                    viewPager.currentItem = MAP_PAGE_INDEX
                }
                R.id.rb_layer_manager -> {
                    viewPager.currentItem =
                        LAYER_PAGE_INDEX
                }
                R.id.rb_query -> {
                    viewPager.currentItem =
                        QUERY_PAGE_INDEX
                }
                R.id.rb_analyst -> {
                    viewPager.currentItem =
                        ANALYST_PAGE_INDEX
                }
            }
        }
    }

    @Subscriber
    private fun reInitMap(event: Location2Map) {
        rb_map.isChecked = true
    }

}