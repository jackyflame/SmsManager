package com.jf.smsmanger.db

import com.haozi.greendaolib.KdSmsEntity
import com.haozi.greendaolib.KdSmsEntityDao
import com.jf.baselibraray.log.LogW
import com.jf.smsmanger.db.base.Rxtask
import com.jf.smsmanger.db.base.TaskCallback
import com.jf.smsmanger.db.base.TaskPresentKt
import com.jf.smsmanger.net.WayTypeEntity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class SmsPresent : TaskPresentKt() {

    fun listWayName(): List<String> {
        val result = ArrayList<String>()
        val SQL_DISTINCT_ENAME = "SELECT DISTINCT " + KdSmsEntityDao.Properties.SmsWayName.columnName + " FROM " + KdSmsEntityDao.TABLENAME
        val c = DBHelper.instance.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null)
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0))
                } while (c.moveToNext())
            }
        } finally {
            c.close()
        }
        result.add("普通短信")
        return result
    }

    fun listWayNameAndCount(isTaken: Boolean?): List<WayTypeEntity> {
        val result = ArrayList<WayTypeEntity>()
        var condition = ""
        if(true == isTaken){
            condition = " WHERE (${KdSmsEntityDao.Properties.TakeMark.columnName} = 'true' OR ${KdSmsEntityDao.Properties.TakeMark.columnName} = 'return')"
        }else if(false  == isTaken){
            condition = " WHERE (${KdSmsEntityDao.Properties.TakeMark.columnName} = '' OR ${KdSmsEntityDao.Properties.TakeMark.columnName} IS NULL)"
        }
        val SQL_ENAME = "SELECT "+KdSmsEntityDao.Properties.SmsWayName.columnName+", COUNT(*) AS MEMCOUNT FROM " +
                KdSmsEntityDao.TABLENAME + condition + " GROUP BY " + KdSmsEntityDao.Properties.SmsWayName.columnName
        LogW.i(SQL_ENAME)
        val c = DBHelper.instance.getDatabase().rawQuery(SQL_ENAME, null)
        try {
            if (c.moveToFirst()) {
                do {
                    var name = c.getString(0)
                    if(name.isNullOrEmpty()){
                        name = "普通短信"
                    }
                    var entity = WayTypeEntity(name,c.getInt(1))
                    result.add(entity)
                } while (c.moveToNext())
            }
        } finally {
            c.close()
        }
        return result
    }

    fun listSmslistByWayType(typeName:String,isTake: Boolean?): List<KdSmsEntity> {
        var queryBuilder = DBHelper.instance.getDaoSession().kdSmsEntityDao.queryBuilder()
        queryBuilder.where(KdSmsEntityDao.Properties.SmsWayName.eq(typeName))
        if(true == isTake){
            queryBuilder.whereOr(
                KdSmsEntityDao.Properties.TakeMark.eq("true"),
                KdSmsEntityDao.Properties.TakeMark.eq("return")
            )
        }else if(false == isTake){
            queryBuilder.whereOr(
                KdSmsEntityDao.Properties.TakeMark.isNull,
                KdSmsEntityDao.Properties.TakeMark.eq("")
            )
        }
        queryBuilder.orderDesc(KdSmsEntityDao.Properties.MsgTime)
        return queryBuilder.list()
    }

    fun updateSmsTakeState(item:KdSmsEntity?,isTake:Boolean){
        if(isTake){
            item?.takeMark = "true"
            item?.takeTime = System.currentTimeMillis()
        }else{
            item?.takeMark = ""
            item?.takeTime = 0
        }
        DBHelper.instance.getDaoSession().kdSmsEntityDao.update(item)
    }

    fun updateSmsReturnState(item:KdSmsEntity?,isReturn:Boolean){
        if(isReturn){
            item?.takeMark = "return"
            item?.takeTime = System.currentTimeMillis()
        }else{
            item?.takeMark = ""
            item?.takeTime = 0
        }
        DBHelper.instance.getDaoSession().kdSmsEntityDao.update(item)
    }

    fun takenAllByWayType(wayName: String,callback: TaskCallback<Boolean>) {
        callTask(object:Rxtask<Boolean>{
            override fun runTask(): Boolean {
                var SQL = "UPDATE ${KdSmsEntityDao.TABLENAME} SET ${KdSmsEntityDao.Properties.TakeMark.columnName} = 'true'," +
                        "${KdSmsEntityDao.Properties.TakeTime.columnName} = ${System.currentTimeMillis()} WHERE " +
                        "${KdSmsEntityDao.Properties.SmsWayName.columnName} = '$wayName'"
                DBHelper.instance.getDatabase().execSQL(SQL)
                return true
            }
        },callback)
    }

    fun untakenAllByWayType(wayName: String,callback: TaskCallback<Boolean>) {
        callTask(object:Rxtask<Boolean>{
            override fun runTask(): Boolean {
                var SQL = "UPDATE ${KdSmsEntityDao.TABLENAME} SET ${KdSmsEntityDao.Properties.TakeMark.columnName} = ''," +
                        "${KdSmsEntityDao.Properties.TakeTime.columnName} = 0 WHERE ${KdSmsEntityDao.Properties.SmsWayName.columnName} = '$wayName'"
                DBHelper.instance.getDatabase().execSQL(SQL)
                return true
            }
        },callback)

    }

    fun deleteAllByWayType(wayName: String, isTake: Boolean?,callback: TaskCallback<Boolean>) {
        callTask(object:Rxtask<Boolean>{
            override fun runTask(): Boolean {
                var queryBuilder = DBHelper.instance.getDaoSession().kdSmsEntityDao.queryBuilder()
                queryBuilder.where(KdSmsEntityDao.Properties.SmsWayName.eq(wayName))
                if(true == isTake){
                    queryBuilder.where(KdSmsEntityDao.Properties.TakeMark.eq("true"))
                }else if(false == isTake){
                    queryBuilder.whereOr(
                        KdSmsEntityDao.Properties.TakeMark.isNull,
                        KdSmsEntityDao.Properties.TakeMark.eq("")
                    )
                }
                queryBuilder.buildDelete().executeDeleteWithoutDetachingEntities()
                return true
            }
        },callback)
    }
}