package com.haozi.greendaolib;

/**
 * Created by Android Studio.
 * ProjectName: EducationApp
 * Author: Haozi
 * Date: 2018/8/8
 * Time: 0:11
 */
public class DBHelper {

    private static class SingletonHolder {
        /***
         * 单例对象实例
         */
        static final DBHelper INSTANCE = new DBHelper();
    }

    public static DBHelper getInstance() {
        return DBHelper.SingletonHolder.INSTANCE;
    }

//    fun getTestCacheById(id:String,testType:String):MycsTestCacheDB?{
//        var queryBuilder = DaoManager.getInstance().daoSession.mycsTestCacheDBDao.queryBuilder()
//        queryBuilder.where(MycsTestCacheDBDao.Properties.Id.eq(id))
//        if(!testType.isNullOrEmpty()){
//            queryBuilder.where( MycsTestCacheDBDao.Properties.TestType.eq(testType))
//        }
//        var list =queryBuilder.list()
//        if(list == null || list.size == 0){
//            return null
//        }
//        return list[0]
//    }

//    fun getMycsTestItemById(id:String):MycsTestItemDB?{
//        //读取问题
//        var dao = DaoManager.getInstance().daoSession.mycsTestItemDBDao
//        var list = dao.queryBuilder()
//                .where(
//                        MycsTestItemDBDao.Properties.Id.eq(id)
//                ).list()
//        if(list == null || list.size == 0){
//            return null
//        }
//        return list[0]
//    }
//
//    fun getMycsTestItemListByParentId(parentId:String):List<MycsTestItemDB>?{
//        //读取问题
//        var dao = DaoManager.getInstance().daoSession.mycsTestItemDBDao
//        var list:List<MycsTestItemDB>? = dao.queryBuilder()
//                .where(
//                        MycsTestItemDBDao.Properties.MainId.eq(parentId)
//                ).list()
//        return list
//    }
//
//    fun saveTestToCache(index : Int?,cache:MycsTestItemDB){
//        //更新主测试记录
//        var parent = DaoManager.getInstance().daoSession.load(MycsTestCacheDB::class.java,cache.mainId)
//        if(parent == null){
//            parent = MycsTestCacheDB()
//            parent.id = cache.mainId
//        }
//        parent.lastFinishIndex = index ?: -1
//        parent.lastFinishId = cache.id
//        parent.testType = cache.testType
//        DaoManager.getInstance().daoSession.insertOrReplace(parent)
//        //更新当前题
//        DaoManager.getInstance().daoSession.insertOrReplace(cache)
//    }
}
