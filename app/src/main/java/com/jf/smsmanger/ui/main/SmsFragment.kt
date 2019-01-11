package com.jf.smsmanger.ui.main

import android.os.Bundle
import android.support.design.widget.TabLayout
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
        initView()
        //初始化webview
        return mRootView
    }

    private fun initView(){
        mBinding.tablayoutCondition?.let {
            it.addTab(it.newTab().setText("全部"))
            it.addTab(it.newTab().setText("未取件"))
            it.addTab(it.newTab().setText("已取件"))
            it.addOnTabSelectedListener(viewModel!!.getTabSlectedListener())
        }
        mBinding.tablayoutCondition?.getTabAt(1)?.select()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.refreshData(1)
    }
}