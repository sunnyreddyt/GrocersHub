package com.grocers.hub.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grocers.hub.database.entities.OfflineCartProduct;

import java.util.List;


@Dao
public interface OfflineCartDao {

    @Query("SELECT * FROM offlineCartProduct")
    OfflineCartProduct getAll();

    @Query("SELECT * FROM offlineCartProduct")
    List<OfflineCartProduct> getAllProducts();

    @Insert
    void insert(OfflineCartProduct offlineCartProduct);

    @Delete
    void delete(OfflineCartProduct offlineCartProduct);

    @Update
    public void update(OfflineCartProduct offlineCartProduct);

    @Query("DELETE FROM offlineCartProduct")
    void truncate();

}
