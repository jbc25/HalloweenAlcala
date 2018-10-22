package org.halloweenalcala.app.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.halloweenalcala.app.model.News;

import java.util.List;


@Dao
public interface NewsDao {


    @Query("SELECT * FROM news ORDER BY datetime DESC")
    List<News> getAll();

    @Query("SELECT * FROM news WHERE id_server = :idServer")
    News getById(int idServer);

    @Insert
    void insertAll(List<News> newsList);

    @Query("DELETE FROM news")
    void deleteAll();

}
