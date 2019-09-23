package com.grocers.hub.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grocers.hub.database.entities.CartOffline;

import java.util.List;

@Dao
public interface CartOfflineDao {

    @Query("SELECT * FROM CartOffline")
    List<CartOffline> getAll();

    @Insert
    void insert(CartOffline cartOffline);

    @Delete
    void delete(CartOffline cartOffline);

    @Update
    void update(CartOffline cartOffline);

    @Query("DELETE FROM CartOffline")
    void truncate();

}