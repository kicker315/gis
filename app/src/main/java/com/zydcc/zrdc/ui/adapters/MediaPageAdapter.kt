package com.zydcc.zrdc.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zydcc.zrdc.ui.view.media.MediaCameraFragment
import com.zydcc.zrdc.ui.view.media.MediaDrawFragment
import com.zydcc.zrdc.ui.view.media.MediaRecordFragment
import com.zydcc.zrdc.ui.view.media.MediaVideoFragment

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/21:5:23 PM
 * ========================================
 */
const val MEDIA_CAMERA_PAGE = 0
const val MEDIA_VIDEO_PAGE = 1
const val MEDIA_RECORD_PAGE = 2
const val MEDIA_DRAW_PAGE = 3

class MediaPageAdapter (fragment: Fragment): FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MEDIA_CAMERA_PAGE to { MediaCameraFragment() },
        MEDIA_VIDEO_PAGE to { MediaVideoFragment() },
        MEDIA_RECORD_PAGE to { MediaRecordFragment() },
        MEDIA_DRAW_PAGE to { MediaDrawFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}