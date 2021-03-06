package com.haozi.greendaolib;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "KD_SMS_ENTITY".
*/
public class KdSmsEntityDao extends AbstractDao<KdSmsEntity, Long> {

    public static final String TABLENAME = "KD_SMS_ENTITY";

    /**
     * Properties of entity KdSmsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CompanyName = new Property(1, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property SmsWayName = new Property(2, String.class, "smsWayName", false, "SMS_WAY_NAME");
        public final static Property CodeNum = new Property(3, String.class, "codeNum", false, "CODE_NUM");
        public final static Property ContractNum = new Property(4, String.class, "contractNum", false, "CONTRACT_NUM");
        public final static Property TakeMark = new Property(5, String.class, "takeMark", false, "TAKE_MARK");
        public final static Property SmsContent = new Property(6, String.class, "smsContent", false, "SMS_CONTENT");
        public final static Property Remark = new Property(7, String.class, "remark", false, "REMARK");
        public final static Property TakeTime = new Property(8, long.class, "takeTime", false, "TAKE_TIME");
        public final static Property MsgTime = new Property(9, long.class, "msgTime", false, "MSG_TIME");
        public final static Property SmsEntityId = new Property(10, Long.class, "smsEntityId", false, "SMS_ENTITY_ID");
    }

    private DaoSession daoSession;


    public KdSmsEntityDao(DaoConfig config) {
        super(config);
    }
    
    public KdSmsEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"KD_SMS_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"COMPANY_NAME\" TEXT," + // 1: companyName
                "\"SMS_WAY_NAME\" TEXT," + // 2: smsWayName
                "\"CODE_NUM\" TEXT," + // 3: codeNum
                "\"CONTRACT_NUM\" TEXT," + // 4: contractNum
                "\"TAKE_MARK\" TEXT," + // 5: takeMark
                "\"SMS_CONTENT\" TEXT," + // 6: smsContent
                "\"REMARK\" TEXT," + // 7: remark
                "\"TAKE_TIME\" INTEGER NOT NULL ," + // 8: takeTime
                "\"MSG_TIME\" INTEGER NOT NULL ," + // 9: msgTime
                "\"SMS_ENTITY_ID\" INTEGER UNIQUE );"); // 10: smsEntityId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"KD_SMS_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, KdSmsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(2, companyName);
        }
 
        String smsWayName = entity.getSmsWayName();
        if (smsWayName != null) {
            stmt.bindString(3, smsWayName);
        }
 
        String codeNum = entity.getCodeNum();
        if (codeNum != null) {
            stmt.bindString(4, codeNum);
        }
 
        String contractNum = entity.getContractNum();
        if (contractNum != null) {
            stmt.bindString(5, contractNum);
        }
 
        String takeMark = entity.getTakeMark();
        if (takeMark != null) {
            stmt.bindString(6, takeMark);
        }
 
        String smsContent = entity.getSmsContent();
        if (smsContent != null) {
            stmt.bindString(7, smsContent);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(8, remark);
        }
        stmt.bindLong(9, entity.getTakeTime());
        stmt.bindLong(10, entity.getMsgTime());
 
        Long smsEntityId = entity.getSmsEntityId();
        if (smsEntityId != null) {
            stmt.bindLong(11, smsEntityId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, KdSmsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(2, companyName);
        }
 
        String smsWayName = entity.getSmsWayName();
        if (smsWayName != null) {
            stmt.bindString(3, smsWayName);
        }
 
        String codeNum = entity.getCodeNum();
        if (codeNum != null) {
            stmt.bindString(4, codeNum);
        }
 
        String contractNum = entity.getContractNum();
        if (contractNum != null) {
            stmt.bindString(5, contractNum);
        }
 
        String takeMark = entity.getTakeMark();
        if (takeMark != null) {
            stmt.bindString(6, takeMark);
        }
 
        String smsContent = entity.getSmsContent();
        if (smsContent != null) {
            stmt.bindString(7, smsContent);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(8, remark);
        }
        stmt.bindLong(9, entity.getTakeTime());
        stmt.bindLong(10, entity.getMsgTime());
 
        Long smsEntityId = entity.getSmsEntityId();
        if (smsEntityId != null) {
            stmt.bindLong(11, smsEntityId);
        }
    }

    @Override
    protected final void attachEntity(KdSmsEntity entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public KdSmsEntity readEntity(Cursor cursor, int offset) {
        KdSmsEntity entity = new KdSmsEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // companyName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // smsWayName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // codeNum
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // contractNum
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // takeMark
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // smsContent
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // remark
            cursor.getLong(offset + 8), // takeTime
            cursor.getLong(offset + 9), // msgTime
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10) // smsEntityId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, KdSmsEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCompanyName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSmsWayName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCodeNum(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setContractNum(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTakeMark(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSmsContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRemark(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTakeTime(cursor.getLong(offset + 8));
        entity.setMsgTime(cursor.getLong(offset + 9));
        entity.setSmsEntityId(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(KdSmsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(KdSmsEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(KdSmsEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getSmsOrginEntityDao().getAllColumns());
            builder.append(" FROM KD_SMS_ENTITY T");
            builder.append(" LEFT JOIN SMS_ORGIN_ENTITY T0 ON T.\"SMS_ENTITY_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected KdSmsEntity loadCurrentDeep(Cursor cursor, boolean lock) {
        KdSmsEntity entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        SmsOrginEntity smsEntity = loadCurrentOther(daoSession.getSmsOrginEntityDao(), cursor, offset);
        entity.setSmsEntity(smsEntity);

        return entity;    
    }

    public KdSmsEntity loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<KdSmsEntity> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<KdSmsEntity> list = new ArrayList<KdSmsEntity>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<KdSmsEntity> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<KdSmsEntity> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
