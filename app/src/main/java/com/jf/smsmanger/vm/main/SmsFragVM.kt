package com.jf.smsmanger.vm.main

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jf.baselibraray.net.ParaKeys
import com.jf.smsmanger.base.BaseSwipeListVM
import com.jf.smsmanger.db.SmsPresent
import com.jf.smsmanger.net.WayTypeEntity
import com.jf.smsmanger.ui.main.SmsFragAdapter
import com.jf.smsmanger.ui.main.SmsFragment
import com.jf.smsmanger.ui.sms.SmsListActivity

class SmsFragVM(var fragment: SmsFragment) : BaseSwipeListVM<SmsPresent, WayTypeEntity, SmsFragAdapter>(fragment.context!!, SmsPresent()) {

    init {
        adapter = SmsFragAdapter()
        adapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            adapter?.getItem(position)?.let {
                var intent = Intent(fragment.context, SmsListActivity::class.java)
                intent.putExtra(ParaKeys.EXTRA_STRING,it.name);
                fragment.startActivity(intent)
            }
        }
    }

    public override fun refreshData(page: Int) {
        adapter?.setNewData(present.listWayNameAndCount())
    }

}