package com.jf.smsmanger.ui.sms

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.haozi.zxwlpro.base.BaseDBActivity
import com.jf.baselibraray.net.ParaKeys
import com.jf.smsmanger.R
import com.jf.smsmanger.databinding.ActivitySmslistBinding
import com.jf.smsmanger.vm.sms.SmsListVM
import com.vondear.rxtool.view.RxToast

class SmsListActivity : BaseDBActivity<ActivitySmslistBinding, SmsListVM>() {

    var takenMark:Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLayout(R.layout.activity_smslist, SmsListVM(this),true)
        var wayName = intent.getStringExtra(ParaKeys.EXTRA_STRING)
        var isTaken = intent.getStringExtra(ParaKeys.EXTRA_TAKENMARK)
        if(wayName.isNullOrEmpty()){
            RxToast.error("获取分类失败")
            finish()
        }else{
            takenMark = when (isTaken) {
                "true" -> true
                "false" -> false
                else -> null
            }
            viewModel?.refreshData(wayName,takenMark)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(true == takenMark){
            menuInflater.inflate(R.menu.menu_delete_untaken,menu)
        }else{
            menuInflater.inflate(R.menu.menu_delete_taken,menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete->{
                viewModel?.showDeletAll()
            }
            R.id.taken->{
                viewModel?.showTakenAll()
            }
            R.id.reset->{
                viewModel?.showResetAll()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}