package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "ConnectionDb",
        foreignKeys = @ForeignKey(entity = ThingDb.class,
                parentColumns = "dbid",
                childColumns = "sourceId",
                onDelete = CASCADE,
                onUpdate = CASCADE),
        indices = @Index("sourceId"))
public class ConnectionDb {
    @PrimaryKey (autoGenerate = true)
    private long dbid;

    private long sourceId;
    private long destinationId;

    private String connectionClass;

    private String connectionInfo;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public String getConnectionClass() {
        return connectionClass;
    }

    public void setConnectionClass(String connectionClass) {
        this.connectionClass = connectionClass;
    }

    public String getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(String connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof ConnectionDb)) {
            return false;
        }

        ConnectionDb c = (ConnectionDb) object;

        return (this.sourceId == c.sourceId) &&
                (this.destinationId == c.destinationId) &&
                (this.connectionClass.equals(c.connectionClass));
    }

    @Override
    public String toString() {
        String string = "";
        String[] c = connectionClass.split("\\.");
        string += String.format("%s:%s %s->%s", dbid, c[c.length-1], sourceId, destinationId);
        return string;
    }
}
