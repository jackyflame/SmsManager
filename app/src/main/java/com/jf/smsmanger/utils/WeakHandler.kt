package com.jf.smsmanger.utils

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

class WeakHandler(listener:RecevieMsgListener): Handler() {

    var target:WeakReference<RecevieMsgListener> = WeakReference(listener)

    override fun handleMessage(msg: Message?) {
        target.get()?.let {
            it.handleMessage(msg)
        }
    }

    interface RecevieMsgListener{
        fun handleMessage(msg: Message?) {
        }
    }
}

