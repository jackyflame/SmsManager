package com.jf.smsmanger.ui.sms

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.haozi.greendaolib.KdSmsEntity
import com.jf.smsmanger.R
import com.vondear.rxtool.RxTimeTool

class SmsListAdapter : BaseQuickAdapter<KdSmsEntity, BaseViewHolder>(R.layout.list_cell_smslist) {

    var takeListener:TakeListener? = null

    override fun convert(helper: BaseViewHolder?, item: KdSmsEntity?) {
        var reciveTime = "通知时间：不详"
        if(item?.msgTime != null){
            reciveTime = "通知时间：${RxTimeTool.milliseconds2String(item.msgTime)}"
        }
        helper?.setText(R.id.txv_recive_time,reciveTime)
        helper?.setText(R.id.txv_smscontent,item?.smsContent?:"没有短信内容")
        if(item?.takeMark == "true"){
            if(item?.takeTime > 0) {
                helper?.setText(R.id.txv_take_time, "取件时间：${RxTimeTool.milliseconds2String(item.takeTime)}")
            }else{
                helper?.setText(R.id.txv_take_time, "取件时间：不详")
            }
            helper?.getView<TextView>(R.id.btn_take)?.let {
                it.setBackgroundResource(R.drawable.bg_round_5_green)
                it.setTextColor(it.resources.getColor(R.color.green))
                it.text = "已取件"
            }
        }else{
            helper?.setText(R.id.txv_take_time, "")
            helper?.getView<TextView>(R.id.btn_take)?.let {
                it.setBackgroundResource(R.drawable.btn_take_selector)
                it.setTextColor(it.resources.getColor(R.color.btn_take_text_selector))
                it.text = "取件"
            }
        }
        helper?.getView<TextView>(R.id.btn_take)?.setOnClickListener {
            if(item?.takeMark == "true"){
                takeListener?.cancelTake(item)
            }else{
                takeListener?.take(item)
            }
        }
    }

    interface TakeListener{
        fun take(item: KdSmsEntity?)
        fun cancelTake(item: KdSmsEntity?)
    }
}