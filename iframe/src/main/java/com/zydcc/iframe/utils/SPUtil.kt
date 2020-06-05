package com.zydcc.iframe.utils

import android.content.Context
import android.content.SharedPreferences
import com.zydcc.iframe.app.BaseApp

/**
 * =======================================
 * SharedPreferences配置文件读写封装
 * Create by ningsikai 2020/6/3:8:47 AM
 * ========================================
 */
object SPUtil {
    private const val DEFAULT_NAME = "shared_data"
    fun putString(key: String?, value: String?) {
        putString(DEFAULT_NAME, key, value)
    }

    fun getString(key: String?, value: String?): String {
        return getString(DEFAULT_NAME, key, value)
    }

    fun removeString(key: String?) {
        removeString(DEFAULT_NAME, key)
    }

    fun putInt(key: String?, value: Int) {
        putInt(DEFAULT_NAME, key, value)
    }

    fun getInt(key: String?, value: Int): Int {
        return getInt(DEFAULT_NAME, key, value)
    }

    fun putBoolean(key: String?, value: Boolean) {
        putBoolean(DEFAULT_NAME, key, value)
    }

    fun getBoolean(key: String?, value: Boolean?): Boolean {
        return getBoolean(DEFAULT_NAME, key, value)
    }

    fun putLong(key: String?, value: Long) {
        putLong(DEFAULT_NAME, key, value)
    }

    fun getLong(key: String?): Long {
        return getLong(DEFAULT_NAME, key)
    }

    fun putFloat(key: String?, value: Float) {
        putFloat(DEFAULT_NAME, key, value)
    }

    fun getFloat(key: String?): Float {
        return getFloat(DEFAULT_NAME, key)
    }

    fun putStringSet(
        key: String?,
        value: Set<String?>?
    ) {
        putStringSet(DEFAULT_NAME, key, value)
    }

    fun getStringSet(key: String?): Set<String> {
        return getStringSet(DEFAULT_NAME, key)
    }

    fun remove(key: String?) {
        remove(DEFAULT_NAME, key)
    }

    operator fun contains(key: String?): Boolean {
        return contains(DEFAULT_NAME, key)
    }

    fun putString(
        fileName: String?,
        key: String?,
        value: String?
    ) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(
        fileName: String?,
        key: String?,
        value: String?
    ): String {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getString(key, value)
    }

    fun removeString(fileName: String?, key: String?) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        editor.apply()
    }

    fun putInt(fileName: String?, key: String?, value: Int) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(fileName: String?, key: String?, value: Int): Int {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getInt(key, value)
    }

    fun putBoolean(
        fileName: String?,
        key: String?,
        value: Boolean
    ) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(
        fileName: String?,
        key: String?,
        value: Boolean?
    ): Boolean {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getBoolean(key, value!!)
    }

    fun putLong(fileName: String?, key: String?, value: Long) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(fileName: String?, key: String?): Long {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getLong(key, 0L)
    }

    fun putFloat(fileName: String?, key: String?, value: Float) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(fileName: String?, key: String?): Float {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getFloat(key, 0f)
    }

    fun putStringSet(
        fileName: String?,
        key: String?,
        value: Set<String?>?
    ) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    fun getStringSet(
        fileName: String?,
        key: String?
    ): Set<String> {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getStringSet(key, null)
    }

    fun remove(fileName: String?, key: String?) {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        editor.apply()
    }

    fun contains(fileName: String?, key: String?): Boolean {
        val sp: SharedPreferences = BaseApp.context
            .getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.contains(key)
    }
}