package com.jf.smsmanger.utils

import android.database.Cursor
import android.net.Uri
import com.haozi.zxwlpro.base.MyApplication
import com.jf.smsmanger.R
import com.vondear.rxtool.view.RxToast
import java.util.regex.Matcher
import java.util.regex.Pattern

object SmsUtils {

    val SMS_URI_ALL = "content://sms/" // 所有短信
    val SMS_URI_INBOX = "content://sms/inbox" // 收件箱
    val SMS_URI_SEND = "content://sms/sent" // 已发送
    val SMS_URI_DRAFT = "content://sms/draft" // 草稿
    val SMS_URI_OUTBOX = "content://sms/outbox" // 发件箱
    val SMS_URI_FAILED = "content://sms/failed" // 发送失败
    val SMS_URI_QUEUED = "content://sms/queued" // 待发送列表

    val cmpArray = MyApplication.instance.resources.getStringArray(R.array.company)

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

    fun getWayName(content:String):String{
        if(content.isNullOrEmpty()){
            return ""
        }
        if(content.startsWith("【")){
            var index = content.indexOfFirst { it == '】' }
            if(index > 0){
                return content.substring(0,index)
            }
        }
        return ""
    }

    fun getCompany(content:String):String{
        if(content.isNullOrEmpty()){
            return ""
        }
        for (item in cmpArray){
            if(content.contains(item)){
                if(item.length <= 2){
                    return item + "快递"
                }
                return item
            }
        }
        return ""
    }

    fun getCodeNum(content:String):String{
        if(content.isNullOrEmpty()){
            return ""
        }
        return ""
        //return getMatcher(content,"(凭“|“|取货码)?([A-Z]|[a-z])?[0-9]+(-)?[0-9]*(-)?[0-9]*(”)?")
    }

    fun getPhoneNum(content:String):String{
        if(content.isNullOrEmpty()){
            return ""
        }
        return ""
        //return getMatcher(content,"[1][3,4,5,6,7,8,9][0-9]{9}")
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     * @param soap
     * @param rgex
     * @return
     */
    fun getMatcher(source:String,rgex:String):String{
        var pattern = Pattern.compile(rgex) // 匹配的模式
        var m = pattern.matcher(source)
        while(m.find()){
            return m.group(1)
        }
        return ""
    }

}