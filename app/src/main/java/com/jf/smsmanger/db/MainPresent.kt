package com.jf.smsmanger.db

import com.haozi.greendaolib.SmsOrginEntity
import com.jf.baselibraray.db.BasePresent
import com.jf.baselibraray.db.WeakAsyncTask
import com.jf.baselibraray.log.LogW
import com.jf.baselibraray.net.retrofit.ReqCallback
import com.jf.baselibraray.uitls.PermissionUtil
import com.jf.baselibraray.view.ErrorDailogActivity
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
                            var sms:SmsOrginEntity? = DBHelper.instance.getSmsOrgin(longDate,null,0,null)
                            if(sms == null){
                                sms = SmsOrginEntity()
                                sms.time = longDate
                                sms.content = strbody
                                sms.person = intPerson
                                sms.address = strAddress
                                sms.type = intType
                                DBHelper.instance.saveSmsOrigin(sms)
                                LogW.i("saveSmsOrigin >> strAddress:$strAddress intPerson:$intPerson " +
                                        "strbody:$strbody time:${RxTimeTool.milliseconds2String(longDate)}($longDate) type:$intType")
                            }else{
                                LogW.i("saveSmsOrigin repeat >> strAddress:$strAddress")
                            }
                            readCount++
                        }
                        LogW.i("sms read >> size:${readCount}")
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

    fun getSmsOrignFromDb():List<SmsOrginEntity>{
        return DBHelper.instance.getSmsList()
    }

    fun getSmsTotal():Long{
        return DBHelper.instance.getSmsTotal()
    }
}