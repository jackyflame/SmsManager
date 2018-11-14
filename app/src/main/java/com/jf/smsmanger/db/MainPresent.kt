package com.jf.smsmanger.db

import com.haozi.greendaolib.SmsOrginEntity
import com.jf.baselibraray.db.BasePresent
import com.jf.baselibraray.db.WeakAsyncTask
import com.jf.baselibraray.log.LogW
import com.jf.baselibraray.net.retrofit.ReqCallback
import com.jf.smsmanger.utils.SmsUtils
import com.vondear.rxtool.RxTimeTool

class MainPresent : BasePresent(){

    fun readSmsToDb(callback: ReqCallback<Boolean>){
        object : WeakAsyncTask<String, Void, Boolean, ReqCallback<Boolean>>(callback) {
            override fun doInBackground(target: ReqCallback<Boolean>, vararg params: String?): Boolean {
                try{
                    SmsUtils.getSmsCursor()?.let {
                        var readCount = 0
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
                            var sms:SmsOrginEntity? = DBHelper.instance.getSmsOrgin(longDate,strbody,intPerson,strAddress)
                            if(sms == null){
                                sms = SmsOrginEntity()
                                sms.time = longDate
                                sms.content = strbody
                                sms.person = intPerson
                                sms.address = strAddress
                                sms.type = intType
                                DBHelper.instance.saveSmsOrigin(sms)
                                LogW.i("saveSmsOrigin >> strAddress:$strAddress intPerson:$intPerson " +
                                        "strbody:$strbody time:${RxTimeTool.milliseconds2String(longDate)} type:$intType")
                            }else{
                                LogW.i("saveSmsOrigin repeat >> strAddress:$strAddress")
                            }
                            readCount++
                        }
                        LogW.i("sms read >> size:${readCount}")
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    return false
                }
                return true
            }
            override fun onPostExecute(target: ReqCallback<Boolean>, result: Boolean) {
                target.onNetResp(result)
            }
        }.executeParallel()
    }

    fun getSmsOrignFromDb():List<SmsOrginEntity>{
        return ArrayList()
    }
}