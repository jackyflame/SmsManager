package com.jf.smsmanger.vm.sms

import com.haozi.greendaolib.KdSmsEntity
import com.jf.smsmanger.base.BaseSwipeListVM
import com.jf.smsmanger.db.SmsPresent
import com.jf.smsmanger.ui.sms.SmsListActivity
import com.jf.smsmanger.ui.sms.SmsListAdapter
import com.vondear.rxtool.view.RxToast
import com.vondear.rxui.view.dialog.RxDialogSureCancel

class SmsListVM (var activity: SmsListActivity) : BaseSwipeListVM<SmsPresent, KdSmsEntity, SmsListAdapter>(activity, SmsPresent()) {

    var wayName:String= ""

    init {
        adapter = SmsListAdapter()
        adapter?.takeListener = object :SmsListAdapter.TakeListener{
            override fun cancelTake(item: KdSmsEntity?) {
                if(item == null || item.id == null || item.id <= 0){
                    RxToast.error("参数错误，取消取件失败")
                }else{
                    var dialog = RxDialogSureCancel(activity)
                    dialog.setContent("是否确认取消取件状态")
                    dialog.setTitle("提醒")
                    dialog.setSureListener {
                        present.updateSmsTakeState(item,false)
                        refreshData(1)
                    }
                    dialog.show()
                }
            }
            override fun take(item: KdSmsEntity?) {
                if(item == null || item.id == null || item.id <= 0){
                    RxToast.error("参数错误，取消取件失败")
                }else{
                    var dialog = RxDialogSureCancel(activity)
                    dialog.setContent("是否确认取件")
                    dialog.setTitle("提醒")
                    dialog.setSureListener {
                        present.updateSmsTakeState(item,true)
                        refreshData(1)
                    }
                    dialog.show()
                }
            }
        }
    }

    public fun refreshData(wayName:String){
        this.wayName = wayName
        refreshData(1)
    }

    override fun refreshData(page: Int) {
        adapter?.setNewData(present.listSmslistByWayType(wayName))
    }

}