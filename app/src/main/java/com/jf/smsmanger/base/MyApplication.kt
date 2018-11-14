package com.haozi.zxwlpro.base

import android.os.Build
import com.haozi.greendaolib.DaoManager
import com.jf.baselibraray.base.BaseApplication
import com.vondear.rxtool.RxTool
import kotlin.properties.Delegates

/**
 * Created by Android Studio.
 * ProjectName: ZxwlPro
 * Author: Haozi
 * Date: 2018/3/29
 * Time: 0:13
 */
class MyApplication : BaseApplication(){

    companion object {
        var instance:MyApplication by Delegates.notNull()
        var deviceID = Build.SERIAL
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RxTool.init(this)
        DaoManager.getInstance().init(this)
    }

    override fun onTerminate() {
        DaoManager.getInstance().closeConnection()
        super.onTerminate()
    }
}