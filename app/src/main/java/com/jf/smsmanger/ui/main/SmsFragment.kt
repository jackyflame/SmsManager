package com.jf.smsmanger.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haozi.zxwlpro.base.BaseDBFragment
import com.jf.smsmanger.R
import com.jf.smsmanger.databinding.FragmentSmsBinding
import com.jf.smsmanger.vm.main.SmsFragVM

class SmsFragment : BaseDBFragment<FragmentSmsBinding, SmsFragVM>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bindLayout(inflater, R.layout.fragment_sms, container!!, SmsFragVM(this))
        viewModel?.initAdapter(mBinding.recyclerList)
        viewModel?.refreshData(1)
        //初始化webview
        return mRootView
    }

}