package com.jf.smsmanger.ui.main

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.haozi.greendaolib.KdSmsEntity
import com.jf.smsmanger.R

class SmsFragAdapter : BaseQuickAdapter<KdSmsEntity, BaseViewHolder>(R.layout.list_cell_wayname) {

    override fun convert(helper: BaseViewHolder?, item: KdSmsEntity?) {
        helper?.setText(R.id.txv_title, item?.smsWayName)
    }

}