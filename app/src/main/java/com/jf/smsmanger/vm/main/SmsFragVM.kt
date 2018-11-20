package com.jf.smsmanger.vm.main

import com.jf.smsmanger.base.BaseSwipeListVM
import com.jf.smsmanger.db.SmsPresent
import com.jf.smsmanger.ui.main.SmsFragAdapter
import com.jf.smsmanger.ui.main.SmsFragment

class SmsFragVM(var fragment: SmsFragment) : BaseSwipeListVM<SmsPresent, String, SmsFragAdapter>(fragment.context!!, SmsPresent()) {

    init {
        adapter = SmsFragAdapter()
    }

    public override fun refreshData(page: Int) {
        adapter?.setNewData(present.listWayName())
    }

}