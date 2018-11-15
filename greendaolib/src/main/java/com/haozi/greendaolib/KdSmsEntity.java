package com.haozi.greendaolib;

import org.greenrobot.greendao.annotation.*;
import org.greenrobot.greendao.DaoException;

@Entity
public class KdSmsEntity {

    @Id(autoincrement = true)
    private Long id;
    /**快递公司*/
    private String companyName;
    /**通道名称*/
    private String smsWayName;
    /**编号*/
    private String codeNum;
    /**联系电话*/
    private String contractNum;
    /**取件标记*/
    private String takeMark;
    /**取件时间*/
    private long takeTime;
    /**短信到达时间*/
    private long msgTime;
    /**短信详情Id*/
    @Unique
    private Long smsEntityId;
    /**短信详情*/
    @ToOne(joinProperty = "smsEntityId")
    private SmsOrginEntity smsEntity;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1848379649)
    private transient KdSmsEntityDao myDao;
    @Generated(hash = 1554837273)
    public KdSmsEntity(Long id, String companyName, String smsWayName,
            String codeNum, String contractNum, String takeMark, long takeTime,
            long msgTime, Long smsEntityId) {
        this.id = id;
        this.companyName = companyName;
        this.smsWayName = smsWayName;
        this.codeNum = codeNum;
        this.contractNum = contractNum;
        this.takeMark = takeMark;
        this.takeTime = takeTime;
        this.msgTime = msgTime;
        this.smsEntityId = smsEntityId;
    }
    @Generated(hash = 711066301)
    public KdSmsEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getSmsWayName() {
        return this.smsWayName;
    }
    public void setSmsWayName(String smsWayName) {
        this.smsWayName = smsWayName;
    }
    public String getCodeNum() {
        return this.codeNum;
    }
    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }
    public String getContractNum() {
        return this.contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getTakeMark() {
        return this.takeMark;
    }
    public void setTakeMark(String takeMark) {
        this.takeMark = takeMark;
    }
    public long getTakeTime() {
        return this.takeTime;
    }
    public void setTakeTime(long takeTime) {
        this.takeTime = takeTime;
    }
    public long getMsgTime() {
        return this.msgTime;
    }
    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }
    public Long getSmsEntityId() {
        return this.smsEntityId;
    }
    public void setSmsEntityId(Long smsEntityId) {
        this.smsEntityId = smsEntityId;
    }
    @Generated(hash = 172086678)
    private transient Long smsEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 863669762)
    public SmsOrginEntity getSmsEntity() {
        Long __key = this.smsEntityId;
        if (smsEntity__resolvedKey == null
                || !smsEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SmsOrginEntityDao targetDao = daoSession.getSmsOrginEntityDao();
            SmsOrginEntity smsEntityNew = targetDao.load(__key);
            synchronized (this) {
                smsEntity = smsEntityNew;
                smsEntity__resolvedKey = __key;
            }
        }
        return smsEntity;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1546111858)
    public void setSmsEntity(SmsOrginEntity smsEntity) {
        synchronized (this) {
            this.smsEntity = smsEntity;
            smsEntityId = smsEntity == null ? null : smsEntity.getId();
            smsEntity__resolvedKey = smsEntityId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2134878637)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getKdSmsEntityDao() : null;
    }
  
}
