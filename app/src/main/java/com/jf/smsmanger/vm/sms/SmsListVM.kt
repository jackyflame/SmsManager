package com.jf.smsmanger.vm.sms

import com.haozi.greendaolib.KdSmsEntity
import com.jf.smsmanger.R
import com.jf.smsmanger.base.BaseSwipeListVM
import com.jf.smsmanger.db.SmsPresent
import com.jf.smsmanger.db.base.TaskCallback
import com.jf.smsmanger.ui.sms.SmsListActivity
import com.jf.smsmanger.ui.sms.SmsListAdapter
import com.jf.smsmanger.utils.AlertUtils
import com.vondear.rxtool.view.RxToast
import com.vondear.rxui.view.RxRoundProgress
import com.vondear.rxui.view.dialog.RxDialogSureCancel
import rx.functions.Action1
import rx.observers.Subscribers

class SmsListVM (var activity: SmsListActivity) : BaseSwipeListVM<SmsPresent, KdSmsEntity, SmsListAdapter>(activity, SmsPresent()) {

    var wayName:String= ""
    var isTaken:Boolean? = null

    init {
        adapter = SmsListAdapter()
        adapter?.takeListener = object :SmsListAdapter.TakeListener{
            override fun cancelTake(item: KdSmsEntity?) {
                if(item == null || item.id == null || item.id <= 0){
                    RxToast.error("参数错误，取消取件失败")
                }else{
                    AlertUtils.showAlertMsgWithCancle(activity,"是否确认取消取件状态",object:AlertUtils.AlertListener{
                        override fun onNeutralListener() {}
                        override fun onNegativeListener() {
                            present.updateSmsTakeState(item,false)
                            refreshData(1)
                        }
                    })
                }
            }
            override fun take(item: KdSmsEntity?) {
                if(item == null || item.id == null || item.id <= 0){
                    RxToast.error("参数错误，取件失败")
                }else{
                    AlertUtils.showAlertMsgWithCancle(activity,"是否确认取件",object:AlertUtils.AlertListener{
                        override fun onNeutralListener() {}
                        override fun onNegativeListener() {
                            present.updateSmsTakeState(item,true)
                            refreshData(1)
                        }
                    })
                }
            }
            override fun cancelBack(item: KdSmsEntity?) {
                if(item == null || item.id == null || item.id <= 0){
                    RxToast.error("参数错误，取消退回快递失败")
                }else{
                    AlertUtils.showAlertMsgWithCancle(activity,"是否确认取消退回快递状态",object:AlertUtils.AlertListener{
                        override fun onNeutralListener() {}
                        override fun onNegativeListener() {
                            present.updateSmsReturnState(item,false)
                            refreshData(1)
                        }
                    })
                }
            }

            override fun retrunBack(item: KdSmsEntity?) {
                if(item == null || item.id == null || item.id <= 0){
                    RxToast.error("参数错误，退回快递失败")
                }else{
                    AlertUtils.showAlertMsgWithCancle(activity,"是否确认退回快递",object:AlertUtils.AlertListener{
                        override fun onNeutralListener() {}
                        override fun onNegativeListener() {
                            present.updateSmsReturnState(item,true)
                            refreshData(1)
                        }
                    })
                }
            }
        }
    }

    fun refreshData(wayName:String,isTaken:Boolean?){
        this.wayName = wayName
        this.isTaken = isTaken
        refreshData(1)
    }

    public override fun refreshData(page: Int) {
        adapter?.setNewData(present.listSmslistByWayType(wayName,isTaken))
    }

    fun showDeletAll() {
        AlertUtils.showAlertMsgWithCancle(activity, R.string.dia_msg_deleteAll,object :AlertUtils.AlertListener{
            override fun onNegativeListener() {
                present.deleteAllByWayType(wayName,isTaken,object :TaskCallback<Boolean>{
                    override fun onStart() {
                    }
                    override fun onSuccess(rst: Boolean) {
                        RxToast.success("操作成功")
                        refreshData(wayName,isTaken)
                    }
                    override fun onError(exception: Throwable) {
                        RxToast.error("操作失败")
                    }
                })
            }
            override fun onNeutralListener() {}
        })
    }

    fun showTakenAll() {
        AlertUtils.showAlertMsgWithCancle(activity,R.string.dia_msg_takenAll,object :AlertUtils.AlertListener{
            override fun onNegativeListener() {
                present.takenAllByWayType(wayName,object :TaskCallback<Boolean>{
                    override fun onStart() {
                    }
                    override fun onSuccess(rst: Boolean) {
                        RxToast.success("操作成功")
                        refreshData(wayName,isTaken)
                    }
                    override fun onError(exception: Throwable) {
                        RxToast.error("操作失败")
                    }
                })
            }
            override fun onNeutralListener() {}
        })
    }

    fun showResetAll() {
        AlertUtils.showAlertMsgWithCancle(activity,R.string.dia_msg_untakenAll,object :AlertUtils.AlertListener{
            override fun onNegativeListener() {
                present.untakenAllByWayType(wayName,object :TaskCallback<Boolean>{
                    override fun onStart() {
                    }
                    override fun onSuccess(rst: Boolean) {
                        RxToast.success("操作成功")
                        refreshData(wayName,isTaken)
                    }
                    override fun onError(exception: Throwable) {
                        RxToast.error("操作失败")
                    }
                })
            }
            override fun onNeutralListener() {}
        })
    }

}