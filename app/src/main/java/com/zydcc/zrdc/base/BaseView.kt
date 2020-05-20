package com.zydcc.zrdc.base

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/20:2:16 PM
 * ========================================
 */
interface BaseView {

    fun showToast(type: Int, msg: String?)


    fun showProgressDialog(title:String,iscancel:Boolean)

    fun closeProgressDialog()
}