package com.ningsk.zrdc.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ningsk.zrdc.ui.main.analyst.AnalystFragment
import com.ningsk.zrdc.ui.main.layermanager.LayerManagerFragment
import com.ningsk.zrdc.ui.main.map.MapFragment
import com.ningsk.zrdc.ui.main.query.QueryFragment

/**
 * =======================================
 * 主页面[Fragment]适配器
 * Create by ningsikai 2020/5/19:1:58 PM
 * ========================================
 */

const val MAP_PAGE_INDEX = 0
const val LAYER_PAGE_INDEX = 1
const val QUERY_PAGE_INDEX = 2
const val ANALYST_PAGE_INDEX = 3

class MainPageAdapter (fragment: Fragment): FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MAP_PAGE_INDEX to { MapFragment() },
        LAYER_PAGE_INDEX to { LayerManagerFragment() },
        QUERY_PAGE_INDEX to { QueryFragment() },
        ANALYST_PAGE_INDEX to { AnalystFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }



}