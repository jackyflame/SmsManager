package com.jf.smsmanger.utils

import android.database.Cursor
import android.net.Uri
import com.haozi.zxwlpro.base.MyApplication
import com.vondear.rxtool.view.RxToast

object SmsUtils {

    val SMS_URI_ALL = "content://sms/" // 所有短信
    val SMS_URI_INBOX = "content://sms/inbox" // 收件箱
    val SMS_URI_SEND = "content://sms/sent" // 已发送
    val SMS_URI_DRAFT = "content://sms/draft" // 草稿
    val SMS_URI_OUTBOX = "content://sms/outbox" // 发件箱
    val SMS_URI_FAILED = "content://sms/failed" // 发送失败
    val SMS_URI_QUEUED = "content://sms/queued" // 待发送列表

    fun getSmsCursor():Cursor?{
        try {
            var uri = Uri.parse(SMS_URI_ALL)
            val projection = arrayOf("_id", "address", "person", "body", "date", "type")
            var cur = MyApplication.instance.contentResolver.query(uri, projection,
                null, null, "date desc") // 获取手机内部短信
            if(cur == null){
                RxToast.error("读取短信失败")
            }
            return cur
        }catch (e:Exception){
            e.printStackTrace()
            RxToast.error("读取短信权限不足，请允许本应用的短信读取权限")
        }
        return null
    }
}