package org.halloweenalcala.app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.halloweenalcala.app.model.Place;

import java.util.List;


@Dao
public interface PlaceDao {


    @Query("SELECT * FROM place WHERE visible = 1 ORDER BY id_server")
    List<Place> getAll();

    @Query("SELECT * FROM place WHERE id_server = :idServer")
    Place getById(int idServer);

    @Insert
    void insertAll(List<Place> places);

    @Query("DELETE FROM place")
    void deleteAll();

//    @Insert
//    void insert(Category category);
//
//    @Update
//    void update(Category category);
//
//    @Delete
//    void delete(Category category);

}
