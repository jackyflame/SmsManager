package com.jf.smsmanger.ui.main

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jf.smsmanger.R
import com.jf.smsmanger.net.WayTypeEntity

class SmsFragAdapter : BaseQuickAdapter<WayTypeEntity, BaseViewHolder>(R.layout.list_cell_wayname) {

    override fun convert(helper: BaseViewHolder?, item: WayTypeEntity?) {
        var title = "${item?.name?:""} (${item?.count})"
        helper?.setText(R.id.txv_title, title)
    }

}