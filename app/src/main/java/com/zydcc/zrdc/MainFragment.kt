package com.zydcc.zrdc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.zydcc.zrdc.adapters.DLTBAdapter
import com.zydcc.zrdc.databinding.FragmentMainBinding
import com.zydcc.zrdc.utilities.InjectorUtils
import com.zydcc.zrdc.viewmodels.DLTBListViewModel

/**
 * =======================================
 *
 * Create by ningsikai 2020/5/18:4:18 PM
 * ========================================
 */
class MainFragment: Fragment() {

    private val viewModel: DLTBListViewModel by viewModels {
        InjectorUtils.provideDLTBListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = DLTBAdapter()
        binding.dltbList.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }


    private fun subscribeUi(adapter: DLTBAdapter) {
        viewModel.dltbs.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}