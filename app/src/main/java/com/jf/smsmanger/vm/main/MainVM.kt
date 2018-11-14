package com.jf.smsmanger.vm.main

import android.databinding.Bindable
import com.jf.baselibraray.event.HttpEvent
import com.jf.baselibraray.net.retrofit.ReqCallback
import com.jf.smsmanger.base.vm.BaseVM
import com.jf.smsmanger.db.MainPresent
import com.jf.smsmanger.ui.main.MainActivity
import com.jf.smsmanger.BR
import com.vondear.rxtool.view.RxToast

class MainVM(private var mActivity: MainActivity): BaseVM<MainPresent>(MainPresent()) {
    /*------------------------------------------DATABIDING----------------------------------------*/
    @Bindable
    var title = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    /*------------------------------------------DATABIDING END------------------------------------*/

    init{
        this.mActivity = mActivity
        mPresent?.readSmsToDb(object :ReqCallback<Boolean>{
            override fun onReqStart() { }
            override fun onNetResp(response: Boolean?) {
                RxToast.success("读取短信成功")
            }
            override fun onReqError(httpEvent: HttpEvent?) {
                RxToast.error("读取短信失败")
            }
        })
    }
}