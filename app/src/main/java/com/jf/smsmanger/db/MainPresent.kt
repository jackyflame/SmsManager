package com.jf.smsmanger.db

import com.haozi.greendaolib.SmsOrginEntity
import com.haozi.zxwlpro.base.MyApplication
import com.jf.baselibraray.db.BasePresent
import com.jf.baselibraray.db.WeakAsyncTask
import com.jf.baselibraray.log.LogW
import com.jf.baselibraray.net.retrofit.ReqCallback
import com.jf.baselibraray.uitls.PermissionUtil
import com.jf.baselibraray.view.ErrorDailogActivity
import com.jf.smsmanger.R
import com.jf.smsmanger.utils.SmsUtils
import com.vondear.rxtool.RxTimeTool

class MainPresent : BasePresent(){

    fun readSmsToDb(callback: ReqCallback<Int>){
        object : WeakAsyncTask<String, Void, Int, ReqCallback<Int>>(callback) {
            override fun doInBackground(target: ReqCallback<Int>, vararg params: String?): Int {
                var readCount = 0
                try{
                    SmsUtils.getSmsCursor()?.let {
                        while(it.moveToNext()){
                            val index_Address = it.getColumnIndex("address")
                            val index_Person = it.getColumnIndex("person")
                            val index_Body = it.getColumnIndex("body")
                            val index_Date = it.getColumnIndex("date")
                            val index_Type = it.getColumnIndex("type")
                            //读取数据
                            val strAddress = it.getString(index_Address)
                            val intPerson = it.getInt(index_Person)
                            val strbody = it.getString(index_Body)
                            val longDate = it.getLong(index_Date)
                            val intType = it.getInt(index_Type)
                            //写入数据库
                            //var sms:SmsOrginEntity? = DBHelper.instance.getSmsOrgin(longDate,strbody,0,strAddress)
                            var sms:SmsOrginEntity? = DBHelper.instance.getSmsOrginByContent(strbody)
                            if(sms == null){
                                sms = SmsOrginEntity()
                                sms.time = longDate
                                sms.content = strbody
                                sms.person = intPerson
                                sms.address = strAddress
                                sms.type = intType
                                sms.id = DBHelper.instance.saveSmsOrigin(sms)
                                checkSaveKdSms(sms)
                            }else{
                                LogW.i("saveSmsOrigin repeat >> strAddress:$strAddress")
                            }
                            readCount++
                        }
                        LogW.i("sms read >> size:$readCount")
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    PermissionUtil.showPerrmissionErrorTips(false,"读取短信权限",null)
                    return -1
                }
                return readCount
            }
            override fun onPostExecute(target: ReqCallback<Int>, result: Int) {
                target.onNetResp(result)
            }
        }.executeParallel()
    }

    fun checkSaveKdSms(sms:SmsOrginEntity){
        if(sms.content.startsWith("【") && (sms.content.contains("快递") || sms.content.contains("外卖")
                    || sms.content.contains("物流") || sms.content.contains("包裹"))){
            DBHelper.instance.saveKdSmsInfo(sms)
        }else{

        }
    }

    fun getSmsOrignFromDb():List<SmsOrginEntity>{
        return DBHelper.instance.getSmsList()
    }

    fun getSmsTotal():Long{
        return DBHelper.instance.getSmsTotal()
    }
}