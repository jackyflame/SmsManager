package com.jf.smsmanger.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haozi.zxwlpro.base.BaseDBFragment
import com.jf.smsmanger.R
import com.jf.smsmanger.databinding.FragmentUserBinding
import com.jf.smsmanger.vm.main.UserVM

class UserFragment : BaseDBFragment<FragmentUserBinding, UserVM>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bindLayout(inflater, R.layout.fragment_user, container!!, UserVM(this))
        return mRootView
    }
}