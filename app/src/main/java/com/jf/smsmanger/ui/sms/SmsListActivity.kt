package com.jf.smsmanger.ui.sms

import android.os.Bundle
import com.haozi.zxwlpro.base.BaseDBActivity
import com.jf.baselibraray.net.ParaKeys
import com.jf.smsmanger.R
import com.jf.smsmanger.databinding.ActivitySmslistBinding
import com.jf.smsmanger.vm.sms.SmsListVM
import com.vondear.rxtool.view.RxToast

class SmsListActivity : BaseDBActivity<ActivitySmslistBinding, SmsListVM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLayout(R.layout.activity_smslist, SmsListVM(this),false)
        var wayName = intent.getStringExtra(ParaKeys.EXTRA_STRING)
        if(wayName.isNullOrEmpty()){
            RxToast.error("获取分类失败")
            finish()
        }else{
            viewModel?.refreshData(wayName)
        }
    }
}