package com.jf.smsmanger.utils

import android.content.Context
import android.support.v7.app.AlertDialog

object AlertUtils {

    interface AlertListener{
        fun onNegativeListener()
        fun onNeutralListener()
    }

    fun showAlertMsg(context:Context,msg:String):AlertDialog{
        return showAlertMsg(context,"提醒", msg, false, null)
    }

    fun showAlertMsgWithCancle(context:Context,msg:String,listener: AlertListener?):AlertDialog{
        return showAlertMsg(context,"提醒", msg, true, listener)
    }

    fun showAlertMsgWithCancle(context:Context,msg:Int,listener: AlertListener?):AlertDialog{
        return showAlertMsg(context,"提醒", context.getString(msg), true, listener)
    }

    fun showAlertMsg(context:Context,title:String, msg:String,withCancel:Boolean,listener: AlertListener?):AlertDialog{
        var builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setNegativeButton("确认") { dialog, _ ->
            listener?.onNegativeListener()
            dialog.dismiss()
        }
        if (withCancel){
            builder.setNeutralButton("取消"){ dialog, _ ->
                listener?.onNeutralListener()
                dialog.dismiss()
            }
        }
        var alertDialog = builder.create()
        alertDialog.show()
        return alertDialog
    }

}