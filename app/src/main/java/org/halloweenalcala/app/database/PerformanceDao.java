package org.halloweenalcala.app.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.halloweenalcala.app.model.Performance;

import java.util.List;


@Dao
public interface PerformanceDao {


    @Query("SELECT * FROM performance ORDER BY date")
    List<Performance> getAll();

    @Query("SELECT * FROM performance WHERE id_place = :idPlaceServer ORDER BY date")
    List<Performance> getPerformancesOfPlace(int idPlaceServer);

    @Insert
    void insertAll(List<Performance> performances);

    @Query("DELETE FROM performance")
    void deleteAll();
}
