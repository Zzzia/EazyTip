package com.zia.notice.database.electric;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by zia on 2018/5/6.
 */
@Entity(tableName = "electric")
public class Electric implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int electricid;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "createTime")
    private String createTime;
    @ColumnInfo(name = "buyTime")
    private String buyTime;
    @ColumnInfo(name = "FixTime")
    private String FixTime;
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;
    @ColumnInfo(name = "model")
    private String model;

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

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getFixTime() {
        return FixTime;
    }

    public void setFixTime(String fixTime) {
        FixTime = fixTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getElectricid() {
        return electricid;
    }

    public void setElectricid(int electricid) {
        this.electricid = electricid;
    }

    @Override
    public String toString() {
        return "Electric{" +
                "name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", buyTime='" + buyTime + '\'' +
                ", FixTime='" + FixTime + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
