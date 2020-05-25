package com.zydcc.zrdc.viewmodels

import android.os.Environment
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.zydcc.zrdc.core.ext.postNext
import com.zydcc.zrdc.core.ext.setNext
import com.zydcc.zrdc.model.bean.FileItem
import com.zydcc.zrdc.utilities.FileUtils
import kotlinx.coroutines.launch
import java.io.File

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/25:1:10 PM
 * ========================================
 */
class DatasourceChooseViewModel : ViewModel() {

    private val _viewStateLiveData: MutableLiveData<DatasourceViewState> = MutableLiveData(DatasourceViewState.initial())

    val viewStateLiveData: LiveData<DatasourceViewState> = _viewStateLiveData

    var suffix = ".tpk"



    @MainThread
    fun refreshDatasource() {
        _viewStateLiveData.setNext { last ->
            last.copy(isLoading = true, throwable = null, pagedList = null)
        }

        viewModelScope.launch {
            val data = getData(suffix)
            _viewStateLiveData.postNext { last ->
                last.copy(isLoading = false, throwable = null, pagedList = data)
            }
        }
    }

    private fun getData(suffix: String): List<FileItem> {

        val files = FileUtils.listFilesInDirWithFilter(File(Environment.getExternalStorageDirectory().absolutePath + "/莱西一张图/"), suffix)
        val data = mutableListOf<FileItem>()
        for (file in files) {
            data.add(FileItem(file.name, file.absolutePath))
        }
        return data
    }




}

data class DatasourceViewState(
    val isLoading: Boolean,
    val throwable: Throwable?,
    val pagedList: List<FileItem>?
) {
    companion object {

        fun initial(): DatasourceViewState {
            return DatasourceViewState(
                isLoading =  false,
                throwable = null,
                pagedList = null
            )
        }

    }
}



@Suppress("UNCHECKED_CAST")
class DatasourceChooseViewModelFactory(): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DatasourceChooseViewModel() as T
    }

}