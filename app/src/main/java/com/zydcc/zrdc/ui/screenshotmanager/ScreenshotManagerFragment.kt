package com.zydcc.zrdc.ui.screenshotmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.zydcc.zrdc.R
import kotlinx.android.synthetic.main.fragment_screenshot_manager.*

/**
 * =======================================
 * 截图管理
 * Create by ningsikai 2020/5/20:9:05 PM
 * ========================================
 */
class ScreenshotManagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.fragment_screenshot_manager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tool_bar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

}