package com.jf.smsmanger.db

import com.haozi.greendaolib.*
import com.jf.baselibraray.log.LogW
import com.jf.smsmanger.utils.SmsUtils
import com.vondear.rxtool.RxTimeTool
import org.greenrobot.greendao.database.Database

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

    fun getDaoSession(): DaoSession {
        return DaoManager.getInstance().daoSession
    }

    fun getDatabase(): Database {
        return DaoManager.getInstance().daoSession.database
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

    fun saveSmsOrigin(sms:SmsOrginEntity?):Long{
        if(sms == null){
            return -1
        }
        return try{
            sms.id = DaoManager.getInstance().daoSession.insertOrReplace(sms)
            LogW.i("[DB] saveSmsOrigin >> id:${sms.id} address:${sms.address} person:${sms.person} " +
                    "content:${sms.content} time:${RxTimeTool.milliseconds2String(sms.time)}(${sms.time}) type:${sms.type}")
            sms.id
        }catch (e:Exception){
            LogW.e("[DB] saveSmsOrigin error:${e.message}")
            -1
        }
    }

    fun saveKdSmsInfo(sms:SmsOrginEntity?):Long{
        if(sms == null || sms.id == null || sms.id <= 0){
            LogW.e("[DB] saveKdSmsInfo error: sms is error or empty")
        }
        var entity = KdSmsEntity()
        entity.smsEntityId = sms!!.id
        entity.msgTime = sms.time
        entity.smsContent = sms.content
        //通道
        entity.smsWayName = SmsUtils.getWayName(sms.content)
        //快递公司
        entity.companyName = SmsUtils.getCompany(sms.content)
        //编号
        entity.codeNum = SmsUtils.getCodeNum(sms.content)
        //联系电话
        entity.contractNum = SmsUtils.getPhoneNum(sms.content)

        return saveKdSmsInfo(entity)
    }

    fun saveKdSmsInfo(entity:KdSmsEntity?):Long{
        if(entity == null){
            return -1
        }
        return try{
            entity.id =  DaoManager.getInstance().daoSession.insertOrReplace(entity)
            LogW.i("[DB] saveKdSmsInfo success>>> id:${entity.id} companyName:${entity.companyName} smsWayName:${entity.smsWayName} " +
                    "codeNum:${entity.codeNum} contractNum:${entity.contractNum} takeMark:${entity.takeMark} takeTime:${entity.takeTime} " +
                    "msgTime:${entity.msgTime} smsEntityId:${entity.smsEntityId}")
            entity.id
        }catch (e:Exception){
            LogW.e("[DB] saveKdSmsInfo error:${e.message}")
            -1
        }
    }
}