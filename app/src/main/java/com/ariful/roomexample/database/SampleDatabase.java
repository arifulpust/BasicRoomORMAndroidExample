package com.ariful.roomexample.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ariful.roomexample.model.University;
import com.ariful.roomexample.query.DaoAccess;

/**
 * Created by Dream71 on 28/01/2018.
 */

@Database(entities = {University.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
    private static SampleDatabase INSTANCE;
  // public abstract DaoAccess userDao();

    public static SampleDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), SampleDatabase.class, "sample-db")
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