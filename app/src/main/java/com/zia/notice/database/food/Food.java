package com.zia.notice.database.food;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by zia on 2018/5/6.
 */
@Entity(tableName = "food")
public class Food implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int foodid;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "createTime")
    private String createTime;
    @ColumnInfo(name = "liveTime")
    private String liveTime;
    @ColumnInfo(name = "endTime")
    private String endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", liveTime='" + liveTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
