package com.grocers.hub.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.grocers.hub.database.dao.OfflineCartDao;
import com.grocers.hub.database.entities.OfflineCartProduct;


@Database(entities = {OfflineCartProduct.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OfflineCartDao offlineCartDao();

}
