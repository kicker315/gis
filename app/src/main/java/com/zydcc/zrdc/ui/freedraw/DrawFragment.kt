package com.zydcc.zrdc.ui.freedraw

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.core.ext.observe
import kotlinx.android.synthetic.main.actionbar_common.*
import kotlinx.android.synthetic.main.fragment_draw.*

/**
 * =======================================
 * 绘制草图界面
 * Create by ningsikai 2020/5/21:4:52 PM
 * ========================================
 */
class DrawFragment: Fragment() {


    val viewModel by viewModels<DrawViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_draw, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        tv_title.text = getString(R.string.txt_draw_layer)
        initListener()
        slider_thickness.max = ((THICKNESS_MAX - THICKNESS_MIN) / THICKNESS_STEP)
        slider_thickness.progress = ((free_draw_view.paintWidth - THICKNESS_MIN) / THICKNESS_STEP).toInt()
        viewModel.currentBackgroundColor.value = Color.BLACK
        observe(viewModel.currentBackgroundColor) {
            tv_color.setBackgroundColor(it)
            free_draw_view.paintColor = it
        }
    }


    private fun initListener() {
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }
        tv_color.setOnClickListener {
            getColorPickerDialog().show()
        }
        rb_undo.setOnClickListener {
            free_draw_view.undoLast()
        }
        rb_redo.setOnClickListener {
            free_draw_view.undoLast()
        }
        rb_clear.setOnClickListener {
            free_draw_view.clearDrawAndHistory()
        }

        slider_thickness.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                free_draw_view.setPaintWidthPx((THICKNESS_MIN + (progress * THICKNESS_STEP)).toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }






    private fun getColorPickerDialog(): AlertDialog {
        return ColorPickerDialogBuilder
            .with(requireContext())
            .setTitle("请选择颜色")
            .initialColor(viewModel.currentBackgroundColor.value!!)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(getString(R.string.txt_confirm)) { _, _selectColor, _ ->
                viewModel.currentBackgroundColor.value = _selectColor
            }
            .setNegativeButton(getString(R.string.txt_cancel)) { _, _ ->

            }
            .build()

    }


    companion object {
        private const val THICKNESS_STEP = 2
        private const val THICKNESS_MAX = 80
        private const val THICKNESS_MIN = 4
    }

}