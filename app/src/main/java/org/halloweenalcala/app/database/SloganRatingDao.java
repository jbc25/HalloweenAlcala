package org.halloweenalcala.app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
