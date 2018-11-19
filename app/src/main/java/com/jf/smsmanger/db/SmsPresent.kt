package com.jf.smsmanger.db

import com.haozi.greendaolib.KdSmsEntityDao
import com.jf.baselibraray.db.BasePresent


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
        return result
    }

}