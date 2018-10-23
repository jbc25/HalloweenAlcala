package org.halloweenalcala.app.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.halloweenalcala.app.model.cloud.SloganRating;


@Dao
public interface SloganRatingDao {


//    @Query("SELECT * FROM sloganrating")
//    List<Place> getAll();

    @Query("SELECT * FROM sloganrating WHERE idSlogan = :idSlogan")
    SloganRating getRatingOfSlogan(String idSlogan);

    @Insert
    void insert(SloganRating sloganRating);

    @Update
    void update(SloganRating sloganRating);

//    @Insert
//    void insert(Category category);
//
//
//    @Delete
//    void delete(Category category);

}
