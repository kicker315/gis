package com.ningsk.zrdc.ui.media

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ningsk.zrdc.R

import com.ningsk.zrdc.utils.IntentConstants
import com.ningsk.zrdc.widget.RecordAudioDialogFragment
import kotlinx.android.synthetic.main.actionbar_common.*
import kotlinx.android.synthetic.main.fragment_media.*

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
      return inflater.inflate(R.layout.fragment_media, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initData()
    }

    private fun initData() {
        tv_title.text = getString(R.string.txt_media)
        vp.adapter = MediaPageAdapter(this)
        // set the icon and text for each tab
        TabLayoutMediator(title_tab, vp) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun initListener() {
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }
        rb_media_camera.setOnClickListener {
            mediaCamera()
        }
        rb_media_record.setOnClickListener {
            mediaRecord()
        }
        rb_media_video.setOnClickListener {
            mediaVideo()
        }
        rb_media_draw.setOnClickListener {
            val navDirections =
                MediaFragmentDirections.actionMediaToDraw()
            findNavController().navigate(navDirections)
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

    private fun mediaCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IntentConstants.REQUEST_CODE_TAKE_PHOTO)
    }

    private fun mediaRecord() {
        val recordAudioDialogFragment = RecordAudioDialogFragment()
        recordAudioDialogFragment.show(requireActivity().supportFragmentManager, "record")
        recordAudioDialogFragment.isCancelable = false
    }

    private fun mediaVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, IntentConstants.REQUEST_CODE_TAKE_VIDEO)
    }


}