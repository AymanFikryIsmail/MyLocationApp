package com.objects.mylocation.mylocation.model.helpers.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.objects.mylocation.mylocation.model.helpers.local.dao.AddressDao;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;

/**
 * Created by ayman on 2019-02-18.
 */

@Database(entities = {AddressPojo.class}, version = 1, exportSchema = false)
public abstract class MyAppDB extends RoomDatabase {

    private static MyAppDB INSTANCE;

    public abstract AddressDao addressDao();

    public static MyAppDB getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), MyAppDB.class, "ic_user-database")
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
