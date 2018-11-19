package com.jf.smsmanger.vm.main

import android.app.Activity
import com.haozi.greendaolib.KdSmsEntity
import com.jf.smsmanger.base.BaseSwipeListVM
import com.jf.smsmanger.db.SmsPresent
import com.jf.smsmanger.ui.main.SmsFragAdapter

class SmsFragVM(var activity: Activity) : BaseSwipeListVM<SmsPresent, KdSmsEntity, SmsFragAdapter>(activity, SmsPresent()) {

    override fun refreshData(page: Int) {
    }

}