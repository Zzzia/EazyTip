package com.zia.notice.database.electric;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by zia on 2018/5/6.
 */
@Dao
public interface ElectricDao {
    @Query("SELECT * FROM electric")
    LiveData<List<Electric>> getAll();

    @Insert
    void insert(Electric electric);

    @Delete
    void delete(Electric electric);

    @Update
    void update(Electric electric);
}
