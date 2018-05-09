package com.zia.notice.database.drug;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by zia on 2018/5/6.
 */
@Entity(tableName = "drug")
public class Drug implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int drugid;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "createTime")
    private String createTime;
    @ColumnInfo(name = "liveTime")
    private String liveTime;
    @ColumnInfo(name = "endTime")
    private String endTime;
    @ColumnInfo(name = "material")
    private String material;
    @ColumnInfo(name = "use_material")
    private String use_material;
    @ColumnInfo(name = "notice")
    private String notice;

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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUse_material() {
        return use_material;
    }

    public void setUse_material(String use_material) {
        this.use_material = use_material;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getDrugid() {
        return drugid;
    }

    public void setDrugid(int drugid) {
        this.drugid = drugid;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", liveTime='" + liveTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", material='" + material + '\'' +
                ", use_material='" + use_material + '\'' +
                ", notice='" + notice + '\'' +
                '}';
    }
}
