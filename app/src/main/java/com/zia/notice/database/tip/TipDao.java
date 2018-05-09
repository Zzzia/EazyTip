package com.zia.notice.database.tip;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by zia on 2018/5/9.
 */
@Dao
public interface TipDao {
    @Query("SELECT * FROM tip")
    LiveData<List<Tip>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tip tip);

    @Delete
    void delete(Tip tip);

    @Update
    void update(Tip tip);
}
