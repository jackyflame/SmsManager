package com.jf.smsmanger.vm.main

import android.content.Intent
import android.support.design.widget.TabLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jf.baselibraray.net.ParaKeys
import com.jf.smsmanger.base.BaseSwipeListVM
import com.jf.smsmanger.db.SmsPresent
import com.jf.smsmanger.net.WayTypeEntity
import com.jf.smsmanger.ui.main.SmsFragAdapter
import com.jf.smsmanger.ui.main.SmsFragment
import com.jf.smsmanger.ui.sms.SmsListActivity

class SmsFragVM(var fragment: SmsFragment) : BaseSwipeListVM<SmsPresent, WayTypeEntity, SmsFragAdapter>(fragment.context!!, SmsPresent()) {

    private var tabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private var isTaken:Boolean? = null

    init {
        adapter = SmsFragAdapter()
        adapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            adapter?.getItem(position)?.let {
                var intent = Intent(fragment.context, SmsListActivity::class.java)
                intent.putExtra(ParaKeys.EXTRA_STRING,it.name)
                var mark = when (isTaken) {
                    true -> "true"
                    false -> "false"
                    else -> null
                }
                intent.putExtra(ParaKeys.EXTRA_TAKENMARK,mark)
                fragment.startActivity(intent)
            }
        }
    }

    public override fun refreshData(page: Int) {
        adapter?.setNewData(present.listWayNameAndCount(isTaken))
    }

    fun getTabSlectedListener(): TabLayout.OnTabSelectedListener {
        if(tabSelectedListener == null){
            tabSelectedListener = object: TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            //全部
                            isTaken = null
                        }
                        1 -> {
                            //未取件
                            isTaken = false
                        }
                        2 -> {
                            //已取件
                            isTaken = true
                        }
                    }
                    refreshData(1)
                }
            }
        }
        return tabSelectedListener!!
    }

}