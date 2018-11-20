package com.jf.smsmanger.db

import com.haozi.greendaolib.KdSmsEntityDao
import com.jf.baselibraray.db.BasePresent
import com.jf.smsmanger.net.WayTypeEntity

class SmsPresent : BasePresent() {

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

    fun listWayNameAndCount(): List<WayTypeEntity> {
        val result = ArrayList<WayTypeEntity>()
        val SQL_ENAME = "SELECT "+KdSmsEntityDao.Properties.SmsWayName.columnName+", COUNT(*) FROM " + KdSmsEntityDao.TABLENAME + " GROUP BY " + KdSmsEntityDao.Properties.SmsWayName.columnName
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

}