package com.haozi.greendaolib;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SMS_ORGIN_ENTITY".
*/
public class SmsOrginEntityDao extends AbstractDao<SmsOrginEntity, Long> {

    public static final String TABLENAME = "SMS_ORGIN_ENTITY";

    /**
     * Properties of entity SmsOrginEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Type = new Property(2, int.class, "type", false, "TYPE");
        public final static Property Time = new Property(3, long.class, "time", false, "TIME");
        public final static Property Person = new Property(4, int.class, "person", false, "PERSON");
        public final static Property Address = new Property(5, String.class, "address", false, "ADDRESS");
    }


    public SmsOrginEntityDao(DaoConfig config) {
        super(config);
    }
    
    public SmsOrginEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SMS_ORGIN_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CONTENT\" TEXT," + // 1: content
                "\"TYPE\" INTEGER NOT NULL ," + // 2: type
                "\"TIME\" INTEGER NOT NULL ," + // 3: time
                "\"PERSON\" INTEGER NOT NULL ," + // 4: person
                "\"ADDRESS\" TEXT);"); // 5: address
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SMS_ORGIN_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SmsOrginEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
        stmt.bindLong(3, entity.getType());
        stmt.bindLong(4, entity.getTime());
        stmt.bindLong(5, entity.getPerson());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(6, address);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SmsOrginEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
        stmt.bindLong(3, entity.getType());
        stmt.bindLong(4, entity.getTime());
        stmt.bindLong(5, entity.getPerson());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(6, address);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SmsOrginEntity readEntity(Cursor cursor, int offset) {
        SmsOrginEntity entity = new SmsOrginEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // content
            cursor.getInt(offset + 2), // type
            cursor.getLong(offset + 3), // time
            cursor.getInt(offset + 4), // person
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // address
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SmsOrginEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setType(cursor.getInt(offset + 2));
        entity.setTime(cursor.getLong(offset + 3));
        entity.setPerson(cursor.getInt(offset + 4));
        entity.setAddress(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SmsOrginEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SmsOrginEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SmsOrginEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
