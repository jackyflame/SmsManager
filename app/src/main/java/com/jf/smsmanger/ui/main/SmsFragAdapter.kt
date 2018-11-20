package com.jf.smsmanger.ui.main

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jf.smsmanger.R

class SmsFragAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.list_cell_wayname) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.txv_title, item?:"普通短信")
    }

}