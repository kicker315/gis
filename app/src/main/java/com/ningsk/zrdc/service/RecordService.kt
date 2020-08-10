package com.ningsk.zrdc.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.TimeUtils
import com.ningsk.zrdc.utils.IntentConstants
import com.ningsk.zrdc.utils.SPConstants
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * =======================================
 * 录音服务
 * Create by ningsikai 2020/8/4:2:47 PM
 * ========================================
 */
class RecordService : Service(){

    private var fid = ""
    private var mElapsedMills = 0L
    private var mFileName = ""
    private var mFilePath = ""
    private var mRecorder: MediaRecorder ?= null
    private var mStartingTimeMills = 0L
    private var recordPath = ""

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        recordPath = intent?.getStringExtra(IntentConstants.URL) ?: ""
        fid = intent?.getStringExtra(IntentConstants.FID) ?: ""
        startRecording()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setFileNameAndPath() {
        mFileName = StringBuilder()
            .append(TimeUtils.getNowString(SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)))
            .append(".amr")
            .toString()
        mFilePath = recordPath + mFileName
        FileUtils.createOrExistsDir(mFilePath)
    }

    fun startRecording() {
        setFileNameAndPath()
        mRecorder = MediaRecorder()
        mRecorder?.let {
            // 设置recorder的输入资源（来源于麦克风，一般可以使用默认DEFAULT）
            it.setAudioSource(MediaRecorder.AudioSource.MIC)
            // 设置输出格式
            it.setOutputFormat(MediaRecorder.AudioEncoder.AMR_NB)
            // 设置编码格式
            it.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            it.setAudioChannels(1)
            it.setAudioSamplingRate(44100)
            it.setAudioEncodingBitRate(192000)
        }
        try {
            this.mRecorder?.prepare()
            this.mRecorder?.start()
            this.mStartingTimeMills = System.currentTimeMillis()
        } catch (ex: IOException) {
            Log.e(LOG_TAG, "prepare() failed")
        }
    }

    fun stopRecording() {
        this.mRecorder?.stop()
        this.mElapsedMills = System.currentTimeMillis() - this.mStartingTimeMills
        this.mRecorder?.release()
        getSharedPreferences(SPConstants.SP_NAME_AUDIO, Context.MODE_PRIVATE)
            .edit()
            .putString(SPConstants.AUDIO_PATH, mFilePath)
            .putLong(SPConstants.ELAPSED, mElapsedMills)
            .apply()
        this.mRecorder = null
    }

    fun renameDialog() {
        Handler().post {
            val edit = EditText(applicationContext)
            edit.setText(mFileName.substring(0, mFileName.indexOf(".amr")))
            val builder = AlertDialog.Builder(applicationContext)
                .setTitle("请输入文件名")
                .setView(edit)
                .setNegativeButton("取消") { _, _ ->
                    FileUtils.delete(mFilePath)
                }
                .setPositiveButton("确定") { _, _ ->
                    val file = FileUtils.getFileByPath(mFilePath)
                    val fileName = edit.text.toString() + ".amr"
                    FileUtils.rename(file, fileName)
                }
            builder.show()
        }
    }


    companion object {
        private val LOG_TAG = RecordService::javaClass.name
    }
}