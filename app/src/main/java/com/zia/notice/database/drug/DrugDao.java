package com.zia.notice.database.drug;

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
public interface DrugDao {
    @Query("SELECT * FROM drug")
    LiveData<List<Drug>> getAll();

    @Insert
    void insert(Drug drug);

    @Delete
    void delete(Drug drug);

    @Update
    void update(Drug drug);
}
