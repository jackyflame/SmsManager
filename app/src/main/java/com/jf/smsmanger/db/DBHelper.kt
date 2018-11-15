package com.jf.smsmanger.db

import com.haozi.greendaolib.DaoManager
import com.haozi.greendaolib.SmsOrginEntity
import com.haozi.greendaolib.SmsOrginEntityDao
import com.jf.baselibraray.log.LogW

/**
 * Created by Android Studio.
 * ProjectName: ZxwlPro
 * Author: Haozi
 * Date: 2018/5/12
 * Time: 20:13
 */
class DBHelper private constructor() {

    private object Holder {
        val INSTANCE = DBHelper()
    }

    companion object {
        val instance: DBHelper by lazy { Holder.INSTANCE }
    }

    fun getSmsTotal():Long{
        return DaoManager.getInstance().daoSession.smsOrginEntityDao.queryBuilder().count()
    }

    fun getSmsList():List<SmsOrginEntity>{
        return DaoManager.getInstance().daoSession.smsOrginEntityDao.queryBuilder().list()
    }

    fun getSmsOrgin(time:Long,content:String?,person:Int,strAddress:String?):SmsOrginEntity?{
        var queryBuilder = DaoManager.getInstance().daoSession.smsOrginEntityDao.queryBuilder()
        queryBuilder.where(SmsOrginEntityDao.Properties.Time.eq(time))
        if(!content.isNullOrEmpty()){
            queryBuilder.where( SmsOrginEntityDao.Properties.Content.eq(content))
        }
        if(!strAddress.isNullOrEmpty()){
            queryBuilder.where( SmsOrginEntityDao.Properties.Address.eq(strAddress))
        }
        if(person > 0){
            queryBuilder.where( SmsOrginEntityDao.Properties.Person.eq(person))
        }
        var list =queryBuilder.list()
        if(list == null || list.size == 0){
            return null
        }
        return list[0]
    }

    fun saveSmsOrigin(cache:SmsOrginEntity?){
        if(cache == null){
            return
        }
        try{
            DaoManager.getInstance().daoSession.insertOrReplace(cache)
            LogW.e("[DB] saveSmsOrigin success")
        }catch (e:Exception){
            LogW.e("[DB] saveSmsOrigin error:${e.message}")
        }

    }
}