package com.grocers.hub.database;

import android.content.Context;

import androidx.room.Room;

import com.grocers.hub.BaseApplication;


/*Class to get reference of room database */
public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class,
                BaseApplication.getContext().getApplicationInfo().dataDir + "/databases/" + "grocersHub.db")
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}