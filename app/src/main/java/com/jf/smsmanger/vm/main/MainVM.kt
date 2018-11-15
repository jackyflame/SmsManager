package com.jf.smsmanger.vm.main

import android.databinding.Bindable
import com.haozi.greendaolib.SmsOrginEntity
import com.jf.baselibraray.event.HttpEvent
import com.jf.baselibraray.log.LogW
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

        var list = mPresent?.getSmsOrignFromDb();
        LogW.i("------------------------->>>> total: ${list?.size}")

        mPresent?.readSmsToDb(object :ReqCallback<Int>{
            override fun onReqStart() { }
            override fun onNetResp(response: Int?) {
                if(response == null || response <= 0){
                    RxToast.error("读取短信失败")
                }else{
                    RxToast.success("读取短信成功$response")
                }
            }
            override fun onReqError(httpEvent: HttpEvent?) {
                RxToast.error("读取短信失败")
            }
        })
    }
}