package org.halloweenalcala.app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.halloweenalcala.app.model.Participant;

import java.util.List;


@Dao
public interface ParticipantDao {


    @Query("SELECT * FROM participant")
    List<Participant> getAll();

    @Insert
    void insertAll(List<Participant> participants);

    @Query("DELETE FROM participant")
    void deleteAll();
}
