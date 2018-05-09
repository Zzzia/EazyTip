package com.zia.notice.database.tip;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by zia on 2018/5/9.
 */
@Entity(tableName = "tip")
public class Tip {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "message")

    private String message;
    @ColumnInfo(name = "time")
    private String time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}