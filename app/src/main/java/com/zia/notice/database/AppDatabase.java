package com.zia.notice.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.zia.notice.database.drug.Drug;
import com.zia.notice.database.drug.DrugDao;
import com.zia.notice.database.electric.Electric;
import com.zia.notice.database.electric.ElectricDao;
import com.zia.notice.database.food.Food;
import com.zia.notice.database.food.FoodDao;
import com.zia.notice.database.tip.Tip;
import com.zia.notice.database.tip.TipDao;

/**
 * Created by zia on 2018/5/6.
 */
@Database(entities = {Drug.class, Electric.class, Food.class, Tip.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "room_db";

    public abstract DrugDao drugDao();

    public abstract ElectricDao electricDao();

    public abstract FoodDao foodDao();

    public abstract TipDao tipDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
