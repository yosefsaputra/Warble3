package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "ThingAccessCredentialDb")
public class ThingAccessCredentialDb {
    @Ignore
    public static final String TAG = "ThingAccessCredentialDb";

    @PrimaryKey (autoGenerate = true)
    private long dbid;

    private String credentialClass;
    private String credentialInfo;

    private long userId;
    private long thingId;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getCredentialClass() {
        return credentialClass;
    }

    public void setCredentialClass(String credentialClass) {
        this.credentialClass = credentialClass;
    }

    public String getCredentialInfo() {
        return credentialInfo;
    }

    public void setCredentialInfo(String credentialInfo) {
        this.credentialInfo = credentialInfo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getThingId() {
        return thingId;
    }

    public void setThingId(long thingId) {
        this.thingId = thingId;
    }
}
