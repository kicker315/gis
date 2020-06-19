package com.zydcc.zrdc.ui.view.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.blankj.utilcode.util.FileUtils
import com.zydcc.zrdc.R
import com.zydcc.zrdc.base.App
import com.zydcc.zrdc.databinding.ActivitySplashBinding
import com.zydcc.zrdc.model.bean.Project
import com.zydcc.zrdc.ui.view.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:9:43 AM
 * ========================================
 */
class SplashActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {


    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        sharedPreferences = getSharedPreferences("zydcc", Context.MODE_PRIVATE)
        requirePermission()
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private fun requirePermission() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            initData()
            return
        }
        EasyPermissions.requestPermissions(this, "一张图需要申请以下权限", RC_CAMERA_AND_LOCATION, *permissions)
    }

    private fun initData() {
        val intent = Intent(this, MainActivity::class.java)
        GlobalScope.launch {
            initDirectory()
            // 如果第一次登陆
            if (sharedPreferences.getBoolean("firststart", true)) {
                initAppID()
                getAppKey()
                val project = Project()
                project.projectName = "默认工程"
                project.url = StringBuilder()
                    .append(Environment.getExternalStorageDirectory().path)
                    .append("/")
                    .append(getString(R.string.app_name))
                    .append("/")
                    .append(getString(R.string.txt_project))
                    .append("/")
                    .append(getString(R.string.txt_default_project))
                    .toString()
                project.startTime = Date()
                project.projectMan = "admain"
                project.coordinate = "西安80坐标系_117"
                val lastProjectId = App.mDaoSession.projectDao.insert(project)

                sharedPreferences.edit()
                    .putLong("lastProject",lastProjectId)
                    .putBoolean("firststart", false)
                    .apply()

            }
            delay(2000)
            startActivity(intent)
            finish()
        }

    }

    private fun initAppID() {

    }

    private fun getAppKey() {

    }

    private fun initDirectory() {
        val rootPath = Environment.getExternalStorageDirectory().path
        val mainDirPath = rootPath + "/" + getString(R.string.app_name)
        // 创建主目录
        FileUtils.createOrExistsDir(mainDirPath)

        val projectDirPath = mainDirPath + "/" + getString(R.string.txt_project)
        // 创建工程目录
        FileUtils.createOrExistsDir(projectDirPath)
        val projectDefaultDirPath = projectDirPath + "/" + getString(R.string.txt_default_project)
        // 创建默认工程目录
        FileUtils.createOrExistsDir(projectDefaultDirPath)
        // 创建JZDATA目录
        FileUtils.createOrExistsDir(projectDefaultDirPath + "/" + getString(R.string.txt_default_project_name))
        // 创建图层数据目录
        val layerDataDirPath = projectDirPath + "/" + getString(R.string.txt_layer_data)
        FileUtils.createOrExistsDir(layerDataDirPath)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        initData()
    }

    companion object {
        private const val RC_CAMERA_AND_LOCATION = 1
    }


}