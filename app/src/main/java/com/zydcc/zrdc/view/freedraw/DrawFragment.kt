package com.zydcc.zrdc.view.freedraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.adapters.SeekBarBindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.zydcc.zrdc.R
import com.zydcc.zrdc.databinding.FragmentDrawBinding
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.viewmodels.DrawViewModel

/**
 * =======================================
 * 绘制草图界面
 * Create by ningsikai 2020/5/21:4:52 PM
 * ========================================
 */
class DrawFragment: Fragment(), SeekBarBindingAdapter.OnProgressChanged {


    val viewModel: DrawViewModel by viewModels {
        InjectorUtils.providerDrawViewModelFactory()
    }

    lateinit var binding: FragmentDrawBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawBinding.inflate(inflater, container, false)
        context ?: return  binding.root

        binding.viewModel = viewModel
        binding.toolBar.setNavigationOnClickListener {view ->
            view.findNavController().navigateUp()
        }
        binding.onProgressChanged = this
        initCallback(binding)
        return binding.root
    }

    private fun initCallback(binding: FragmentDrawBinding) {
        binding.apply {
            sliderThickness.max = ((THICKNESS_MAX - THICKNESS_MIN) / THICKNESS_STEP)
            thicknessProgress = ((freeDrawView.paintWidth - THICKNESS_MIN) / THICKNESS_STEP).toInt()
            val freeDrawView = binding.freeDrawView

            callback = object:
                Callback {
                override fun changeColor() {
                    getColorPickerDialog().show()
                }

                override fun undoLast() {
                    freeDrawView.undoLast()
                }

                override fun redoLast() {
                    freeDrawView.redoLast()
                }

                override fun clear() {
                    freeDrawView.clearDrawAndHistory()
                }

            }

        }

    }



    private fun getColorPickerDialog(): AlertDialog {
        return ColorPickerDialogBuilder
            .with(requireContext())
            .setTitle("请选择颜色")
            .initialColor(viewModel.currentBackgroundColor.get())
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(getString(R.string.txt_confirm)) { _, _selectColor, _ ->
                viewModel.currentBackgroundColor.set(_selectColor)
            }
            .setNegativeButton(getString(R.string.txt_cancel)) { _, _ ->

            }
            .build()

    }

    interface Callback {

        fun changeColor()
        fun undoLast()
        fun redoLast()
        fun clear()

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        binding.freeDrawView.setPaintWidthPx((THICKNESS_MIN + (progress * THICKNESS_STEP)).toFloat())
    }

    companion object {
        private const val THICKNESS_STEP = 2
        private const val THICKNESS_MAX = 80
        private const val THICKNESS_MIN = 4
    }

}