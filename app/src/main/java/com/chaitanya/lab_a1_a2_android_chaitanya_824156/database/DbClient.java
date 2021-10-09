package com.chaitanya.lab_a1_a2_android_chaitanya_824156.database;

import android.content.Context;

import androidx.room.Room;

public class DbClient {

    private static DbClient mInstance;
    private Context mContext;

    private AppDatabase appDatabase;

    private DbClient(Context context) {
        this.mContext = context;
        appDatabase = Room.databaseBuilder(mContext, AppDatabase.class, "chaitanya-db").allowMainThreadQueries().build();
    }

    public static synchronized DbClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DbClient(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDb() {
        return appDatabase;
    }
}