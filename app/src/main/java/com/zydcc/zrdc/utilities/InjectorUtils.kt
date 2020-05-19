package com.zydcc.zrdc.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.zydcc.zrdc.data.AppDatabase
import com.zydcc.zrdc.data.DLTBRepository
import com.zydcc.zrdc.viewmodels.DLTBListViewModelFactory

/**
 * =======================================
 * Static methods used to inject classes needed for various Activities and Fragments
 * Create by ningsikai 2020/5/18:4:06 PM
 * ========================================
 */
object InjectorUtils {

    private fun getDLTBRepository(context: Context): DLTBRepository {
        return DLTBRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).dltbDao()
        )
    }

    fun provideDLTBListViewModelFactory(fragment: Fragment): DLTBListViewModelFactory {
        val repository = getDLTBRepository(fragment.requireContext())
        return DLTBListViewModelFactory(repository, fragment)
    }


}