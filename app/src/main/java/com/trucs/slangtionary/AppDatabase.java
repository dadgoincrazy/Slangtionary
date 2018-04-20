package com.trucs.slangtionary;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by owner on 09/04/2018.
 */
@Database(entities = {Word.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "MyDatabase";

    public abstract WordDao wordDao();

    public static AppDatabase getDB(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyIntance() {
        INSTANCE = null;
    }
}
