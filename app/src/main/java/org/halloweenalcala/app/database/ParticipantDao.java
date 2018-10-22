package org.halloweenalcala.app.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
