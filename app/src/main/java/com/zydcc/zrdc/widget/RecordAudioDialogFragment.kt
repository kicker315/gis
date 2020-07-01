package com.zydcc.zrdc.widget

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.zydcc.zrdc.R
import kotlinx.android.synthetic.main.fragment_record_audio.view.*

/**
 * =======================================
 * 录音dialog
 * Create by ningsikai 2020/6/16:11:31 AM
 * ========================================
 */
class RecordAudioDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireActivity())
        val view = layoutInflater.inflate(R.layout.fragment_record_audio, null)
        view.record_audio_fab_record.setOnClickListener {  }
        view.record_audio_iv_close.setOnClickListener{
            dismiss()
        }
        builder.setCancelable(false)
        builder.setView(view)

        return builder.create()

    }



}