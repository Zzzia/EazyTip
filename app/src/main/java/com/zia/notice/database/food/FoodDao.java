package com.zia.notice.database.food;

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
public interface FoodDao {
    @Query("SELECT * FROM food")
    LiveData<List<Food>> getAll();

    @Insert
    void insert(Food food);

    @Delete
    void delete(Food food);

    @Update
    void update(Food food);
}
